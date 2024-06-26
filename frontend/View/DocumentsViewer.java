package view;

import javax.swing.*;
import model.Document;
import java.awt.*;
import java.util.List;

public class DocumentsViewer extends JPanel {
  public List<Document> documents;
  public JLabel titleLabel = new JLabel("", SwingConstants.CENTER);
  public JTextArea documentsArea;
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

    // prevButton.setAction(new AbstractAction("Prev") {
    // @Override
    // public void actionPerformed(java.awt.event.ActionEvent e) {
    // var off = offset - 1;
    // if (off < 0)
    // off = documents.size() - 1;
    // setOffset(off);
    // }
    // });
    // nextButton.setAction(new AbstractAction("Next") {
    // @Override
    // public void actionPerformed(java.awt.event.ActionEvent e) {
    // var off = offset + 1;
    // if (off >= documents.size())
    // off = 0;
    // setOffset(off);
    // }
    // });
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
}
