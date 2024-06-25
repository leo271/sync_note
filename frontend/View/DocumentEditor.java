package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.Document;

public class DocumentEditor extends JPanel {
  private JLabel titleLabel;
  private JTextArea editArea;
  private JEditorPane previewArea;
  private JButton editButton;
  private JButton previewButton;
  private JButton saveButton;
  private JButton deleteButton;
  private CardLayout cardLayout;
  private JPanel contentPanel;

  public DocumentEditor(Document document) {
    setLayout(new BorderLayout());

    titleLabel = new JLabel("Edit Mode", SwingConstants.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 16)); // フォントを設定（オプション）

    add(titleLabel, BorderLayout.NORTH); // パネルの上部に追加

    // 編集画面
    editArea = new JTextArea(document.content);
    JScrollPane editScrollPane = new JScrollPane(editArea);

    // プレビュー画面
    previewArea = new JEditorPane();
    previewArea.setContentType("text/html");
    previewArea.setEditable(false);
    JScrollPane previewScrollPane = new JScrollPane(previewArea);

    // 編集とプレビューの切り替えカードレイアウト
    cardLayout = new CardLayout();
    contentPanel = new JPanel(cardLayout);
    contentPanel.add(editScrollPane, "EDIT");
    contentPanel.add(previewScrollPane, "PREVIEW");
    add(contentPanel, BorderLayout.CENTER);

    // ボタンパネル
    editButton = new JButton("Edit");
    previewButton = new JButton("Preview");
    saveButton = new JButton("Save");
    deleteButton = new JButton("Delete");

    JPanel bottomPanel = new JPanel(new BorderLayout());
    JPanel buttonPanel = new JPanel(new GridLayout(1, 4));

    // deleteButtonのテキストを赤くする
    deleteButton.setForeground(Color.RED);

    buttonPanel.add(editButton);
    buttonPanel.add(previewButton);
    buttonPanel.add(saveButton);
    buttonPanel.add(deleteButton);

    bottomPanel.add(buttonPanel, BorderLayout.CENTER);
    add(bottomPanel, BorderLayout.SOUTH);


    // アクション
    editButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        cardLayout.show(contentPanel, "EDIT");
        updateTitle("Edit Mode");
      }
    });

    previewButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        updatePreview();
        cardLayout.show(contentPanel, "PREVIEW");
        updateTitle("Preview Mode");
      }
    });
  }

  // 画面上部のタイトル更新
  private void updateTitle(String title) {
    titleLabel.setText(title);
  }

  // プレビュー画面の更新
  private void updatePreview() {
    String markdown = editArea.getText();
    String html = convertMarkdownToHtml(markdown);
    previewArea.setText(html);
  }

  // マークダウン記法を受け取り, プレビューを作成
  private String convertMarkdownToHtml(String markdown) {
    StringBuilder html = new StringBuilder();
    String[] lines = markdown.split("\n");
    boolean inList = false;

    for (String line : lines) {
      if (line.trim().isEmpty()) {
        if (inList) {
          html.append("</ul>\n");
          inList = false;
        }
        html.append("<br>\n");
        continue;
      }

      // ヘッダー
      if (line.startsWith("# ")) {
        html.append("<h1>").append(line.substring(2)).append("</h1>\n");
      } else if (line.startsWith("## ")) {
        html.append("<h2>").append(line.substring(3)).append("</h2>\n");
      } else if (line.startsWith("### ")) {
        html.append("<h3>").append(line.substring(4)).append("</h3>\n");
      }
      // リスト
      else if (line.startsWith("- ")) {
        if (!inList) {
          html.append("<ul>\n");
          inList = true;
        }
        html.append("<li>").append(line.substring(2)).append("</li>\n");
      }

      // Normal paragraph
      else {
        if (inList) {
          html.append("</ul>\n");
          inList = false;
        }
        html.append("<p>").append(line).append("</p>\n");
      }
    }

    if (inList) {
      html.append("</ul>\n");
    }

    String result = html.toString();

    // 太字
    result = result.replaceAll("\\*\\*(.+?)\\*\\*", "<strong>$1</strong>");

    // italic
    result = result.replaceAll("\\*(.+?)\\*", "<em>$1</em>");

    // Convert links
    Pattern linkPattern = Pattern.compile("\\[(.+?)\\]\\((.+?)\\)");
    Matcher linkMatcher = linkPattern.matcher(result);
    StringBuffer sb = new StringBuffer();
    while (linkMatcher.find()) {
      linkMatcher.appendReplacement(sb, "<a href=\"$2\">$1</a>");
    }
    linkMatcher.appendTail(sb);
    result = sb.toString();

    return "<html><body>" + result + "</body></html>";
  }
}
