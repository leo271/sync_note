package view;

import javax.swing.*;
import model.Document;
import java.awt.*;
import java.util.List;

public class DocumentsViewer extends JPanel {
  public List<Document> documents;
  public JLabel titleLabel = new JLabel("", SwingConstants.CENTER);
  public JEditorPane documentsPane;
  public JButton likeButton = new JButton("♥");
  public JButton nextButton = new JButton("Next");
  public JButton prevButton = new JButton("Prev");

  public int offset = 0;
  private int like = 0;
  private String head = "";
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
    likeLabel.setForeground(Color.RED);

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

  public void setOffset(int offset) {
    this.offset = offset;
    try {
      this.like = documents.get(offset).like;
      activeDocument = documents.get(offset);
      documentsPane.setText(activeDocument.content);
    } catch (Exception e) {
      this.like = 0;
      activeDocument = new Document("");
      documentsPane.setText("");
    }
    titleLabel.setText(head + "\t No." + (offset + 1));
    likeLabel.setText("          ♥  :  " + like);
    revalidate();
    repaint();
  }
}
