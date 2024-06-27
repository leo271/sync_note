package view;

import javax.swing.*;
import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.Document;

public class DocumentEditor extends JPanel {
  private JLabel titleLabel = new JLabel("Title", SwingConstants.CENTER);
  public JTextArea editArea; // こちらは一応publicにする
  public JEditorPane previewArea; // プレビューのテキスト参照用
  public JButton editButton = new JButton("編集"); // 編集ボタン
  public JButton previewButton = new JButton("プレビュー"); // プレビューボタン
  public JButton deleteButton = new JButton("このドキュメントを削除"); // 削除ボタン
  public CardLayout cardLayout;
  public JPanel contentPanel;
  public Document document = new Document(""); // 参照用にドキュメント持たせる

  public DocumentEditor() {
    setLayout(new BorderLayout());
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

    JPanel bottomPanel = new JPanel(new BorderLayout());
    JPanel buttonPanel = new JPanel(new GridLayout(1, 4));

    // deleteButtonのテキストを赤くする
    deleteButton.setForeground(Color.RED);

    buttonPanel.add(editButton);
    buttonPanel.add(previewButton);
    buttonPanel.add(deleteButton);

    bottomPanel.add(buttonPanel, BorderLayout.CENTER);
    add(bottomPanel, BorderLayout.SOUTH);

    setEdit(true);
  }

  public void setDocument(Document document) {
    this.document = document;
    editArea.setText(document.content);
    updateTitle(document.head + " - " + "Edit Mode");

    updatePreview();
  }

  public void setEdit(boolean isEdit) {
    if (isEdit) {
      editButton.setForeground(Color.BLUE);
      previewButton.setForeground(Color.BLACK);
      cardLayout.show(contentPanel, "EDIT");
      updateTitle((document.head.isEmpty() ? "TEMP" : document.head) + " - Edit Mode");
    } else {
      updatePreview();
      previewButton.setForeground(Color.BLUE);
      editButton.setForeground(Color.BLACK);
      cardLayout.show(contentPanel, "PREVIEW");
      updateTitle(document.head + " - Preview Mode");
    }
  }

  // 画面上部のタイトル更新
  public void updateTitle(String title) {
    titleLabel.setText(title);
  }

  // プレビュー画面の更新
  public void updatePreview() {
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
