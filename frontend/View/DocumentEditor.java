
package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.Document;

public class DocumentEditor extends JPanel {
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

    // Create edit area
    editArea = new JTextArea(document.content);
    JScrollPane editScrollPane = new JScrollPane(editArea);

    // Create preview area
    previewArea = new JEditorPane();
    previewArea.setContentType("text/html");
    previewArea.setEditable(false);
    JScrollPane previewScrollPane = new JScrollPane(previewArea);

    // Create card layout for switching between edit and preview
    cardLayout = new CardLayout();
    contentPanel = new JPanel(cardLayout);
    contentPanel.add(editScrollPane, "EDIT");
    contentPanel.add(previewScrollPane, "PREVIEW");
    add(contentPanel, BorderLayout.CENTER);

    // Create button panel
    JPanel buttonPanel = new JPanel();
    editButton = new JButton("Edit");
    previewButton = new JButton("Preview");
    saveButton = new JButton("Save");
    deleteButton = new JButton("Delete");

    buttonPanel.add(editButton);
    buttonPanel.add(previewButton);
    buttonPanel.add(saveButton);
    buttonPanel.add(deleteButton);
    add(buttonPanel, BorderLayout.SOUTH);

    // Add action listeners
    editButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        cardLayout.show(contentPanel, "EDIT");
      }
    });

    previewButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        updatePreview();
        cardLayout.show(contentPanel, "PREVIEW");
      }
    });
  }

  private void updatePreview() {
    String markdown = editArea.getText();
    String html = convertMarkdownToHtml(markdown);
    previewArea.setText(html);
  }

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

      // Convert headers
      if (line.startsWith("# ")) {
        html.append("<h1>").append(line.substring(2)).append("</h1>\n");
      } else if (line.startsWith("## ")) {
        html.append("<h2>").append(line.substring(3)).append("</h2>\n");
      } else if (line.startsWith("### ")) {
        html.append("<h3>").append(line.substring(4)).append("</h3>\n");
      }
      // Convert unordered lists
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

    // Convert bold
    result = result.replaceAll("\\*\\*(.+?)\\*\\*", "<strong>$1</strong>");

    // Convert italic
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
