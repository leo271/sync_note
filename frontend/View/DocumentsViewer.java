package view;

import javax.swing.*;
import model.Document;
import model.DocumentController;
import model.Response;
import utility.Colors;
import java.awt.*;
import java.util.List;

public class DocumentsViewer extends JPanel {
  public List<Document> documents;
  public JLabel titleLabel = new JLabel("", SwingConstants.CENTER);
  public JEditorPane documentsPane;
  public JButton likeButton = new JButton("♥");
  public JButton nextButton = new JButton("次");
  public JButton prevButton = new JButton("前");

  public int offset = 0;
  private int like = 0;
  private String head = "";
  private boolean hasLiked = false;
  private boolean isMine = false;
  public JLabel likeLabel = new JLabel("");

  public Document activeDocument; // 現在閲覧中のDocument 参照用に持たせる

  public DocumentsViewer() {
    setLayout(new BorderLayout());

    titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
    add(titleLabel, BorderLayout.NORTH);

    documentsPane = new JEditorPane();
    documentsPane.setContentType("text/html");
    documentsPane.setEditable(false); // ユーザーが編集できないようにする
    add(new JScrollPane(documentsPane), BorderLayout.CENTER); // JEditorPaneをスクロールペインに追加して中央に配置

    JPanel bottomPanel = new JPanel(new BorderLayout());
    JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
    customizeButton(likeButton);
    customizeButton(nextButton);
    customizeButton(prevButton);

    buttonPanel.add(likeButton);
    buttonPanel.add(likeLabel); // ライクの数を表示するラベルを追加
    bottomPanel.add(buttonPanel, BorderLayout.CENTER);
    bottomPanel.add(prevButton, BorderLayout.WEST);
    bottomPanel.add(nextButton, BorderLayout.EAST);
    add(bottomPanel, BorderLayout.SOUTH);
  }

  // Head名を受け取り、それに属するドキュメントを表示するメソッド
  public void setDocuments(String head, List<Document> documents) {
    this.documents = documents;
    this.head = head;
    setOffset(0);
  }

  public void setDocuments(String head, List<Document> documents, String first) {
    this.documents = documents;
    this.head = head;
    for (int i = 0; i < documents.size(); i++) {
      if (documents.get(i).docID.equals(first)) {
        setOffset(i);
        return;
      }
    }
  }

  public void setOffset(int offset) {
    this.offset = offset;
    try {
      this.like = documents.get(offset).like;
      var local = DocumentController.inLocal(documents.get(offset).docID);
      if (local.hasError(Response.INVALID_VALUE)) {
        System.out.println("Error: " + local.error);
        return;
      } else if (local.hasError(Response.NOT_FOUND)) {
        isMine = false;
        hasLiked = false;
      } else if (local.message.equals("M")) {
        isMine = true;
        hasLiked = false;
      } else if (local.message.equals("L")) {
        isMine = false;
        hasLiked = true;
      } else {
        System.out.println("Error: " + local.message);
      }
      if (isMine) {
        likeButton.setEnabled(false);
        likeButton.setText("あなたのドキュメントです👑");
      } else {
        likeButton.setEnabled(true);
        likeButton.setText("♥");
      }
      if (hasLiked) {
        likeButton.setForeground(Colors.red);
        likeLabel.setForeground(Colors.red);
      } else {
        likeButton.setForeground(Colors.white);
        likeLabel.setForeground(Colors.black);
      }

      activeDocument = documents.get(offset);
      documentsPane.setText(DocumentEditor.convertMarkdownToHtml(activeDocument.content));
    } catch (Exception e) {
      this.like = 0;
      activeDocument = new Document("");
      documentsPane.setText("");
    }
    titleLabel.setText(head + "\t No." + (offset + 1));
    likeLabel.setText("          ♥  :  " + like);
    documentsPane.setCaretPosition(0);
    revalidate();
    repaint();
  }

  private void customizeButton(JButton button) {
    button.setBackground(Colors.pale);
    button.setForeground(Colors.white);
    button.setFont(new Font("Arial", Font.PLAIN, 14));
    button.setFocusPainted(false);
    button.setOpaque(true);
    button.setBorderPainted(false);
    button.setBorder(
        BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Colors.pale, 1, true),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)));
    button.setCursor(new Cursor(Cursor.HAND_CURSOR));

    // Add hover effect
    button.addMouseListener(new java.awt.event.MouseAdapter() {
      @Override
      public void mouseEntered(java.awt.event.MouseEvent e) {
        button.setBackground(Colors.lightRed);
      }

      @Override
      public void mouseExited(java.awt.event.MouseEvent e) {
        button.setBackground(Colors.pale);
      }
    });
  }
}
