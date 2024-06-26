package view;

import javax.swing.*;
import model.Document;
import java.awt.*;
import java.util.List;

public class DocumentsViewer extends JPanel {
  private static final List<Document> demoDocuments = List.of(
      new Document("a", "Mathematics", "Mathematics is the study of numbers, shapes, and patterns.",
          10),
      new Document("b", "Mathematics",
          "It is a field of study that is used to understand the world around us.", 5),
      new Document("c", "Mathematics", "Mathematics is the language of science.", 2));

  public List<Document> documents;
  public JLabel titleLabel = new JLabel("", SwingConstants.CENTER);
  public JTextArea documentsArea;
  public JButton likeButton = new JButton("♥");
  public JButton nextButton = new JButton("Next");
  public JButton prevButton = new JButton("Prev");

  private int offset = 0;
  private int like = 0;
  private String head = "";
  public JLabel likeLabel = new JLabel("");

  public Document activeDocument; // 現在閲覧中のDocument 参照用に持たせる

  public DocumentsViewer(String[] initialHeads) {
    setLayout(new BorderLayout());

    titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
    add(titleLabel, BorderLayout.NORTH);

    documentsArea = new JTextArea();
    documentsArea.setEditable(false); // ユーザーが編集できないようにする
    add(new JScrollPane(documentsArea), BorderLayout.CENTER); // JTextAreaをスクロールペインに追加して中央に配置

    JPanel bottomPanel = new JPanel(new BorderLayout());
    JPanel buttonPanel = new JPanel(new GridLayout(1, 2));

    buttonPanel.add(likeButton);
    buttonPanel.add(likeLabel); // ライクの数を表示するラベルを追加
    bottomPanel.add(buttonPanel, BorderLayout.CENTER);
    bottomPanel.add(prevButton, BorderLayout.WEST);
    bottomPanel.add(nextButton, BorderLayout.EAST);
    add(bottomPanel, BorderLayout.SOUTH);
    setDemo();
  }

  // Head名を受け取り、それに属するドキュメントを表示するメソッド
  public void setDocuments(List<Document> documents) {
    this.documents = documents;
    this.head = documents.get(0).head;
    setOffset(0);
  }

  public void setOffset(int offset) {
    this.offset = offset;
    this.like = documents.get(offset).like;
    activeDocument = documents.get(offset);
    documentsArea.setText(documents.get(offset).content);
    titleLabel.setText(head + "\t No." + (offset + 1));
    likeLabel.setText("          ♥  :  " + like);
    revalidate();
    repaint();
  }

  // TODO: デモ用のドキュメントを表示するメソッド、ここのロジックは将来的にVMに移す
  private void setDemo() {
    documents = demoDocuments;
    setDocuments(documents);
    prevButton.setAction(new AbstractAction("Prev") {
      @Override
      public void actionPerformed(java.awt.event.ActionEvent e) {
        var off = offset - 1;
        if (off < 0)
          off = documents.size() - 1;
        setOffset(off);
      }
    });
    nextButton.setAction(new AbstractAction("Next") {
      @Override
      public void actionPerformed(java.awt.event.ActionEvent e) {
        var off = offset + 1;
        if (off >= documents.size())
          off = 0;
        setOffset(off);
      }
    });
  }
}
