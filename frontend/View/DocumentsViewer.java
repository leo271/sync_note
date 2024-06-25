package view;

import javax.swing.*;
import frontend.model.HeadGroup;
import model.Document;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class DocumentsViewer extends JPanel {
    public List<Document> documents;
    public JLabel titleLabel = new JLabel("Document", SwingConstants.CENTER);
    public JTextArea documentsArea;
    public JButton likeButton = new JButton("♥");
    public JLabel likeLabel = new JLabel("          ♥  :   0");


    public DocumentsViewer(String[] initialHeads) {

        setLayout(new BorderLayout());

        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));

        buttonPanel.add(likeButton);
        buttonPanel.add(likeLabel); // ライクの数を表示するラベルを追加
        bottomPanel.add(buttonPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;

        setLayout(new BorderLayout());

        documentsArea = new JTextArea();
        documentsArea.setEditable(false); // 編集不可
        add(new JScrollPane(documentsArea), BorderLayout.CENTER);
    }

    // Head名を受け取り、それに属するドキュメントを表示するメソッド
    public void showDocuments(String headName) {
        titleLabel.setText("Documents for " + headName);
        StringBuilder sb = new StringBuilder();
        for (Document doc : documents) {
            if (doc.head.equals(headName)) {
                sb.append(doc.head).append("\n");
                sb.append(doc.content).append("\n\n");
            }
        }
        documentsArea.setText(sb.toString());

        JPanel buttonPanel = new JPanel();
        likeButton = new JButton("♥");
        buttonPanel.add(likeButton);
    }

    public void showLikes(int like) {
        likeLabel.setText("♥: " + like); // ライクラベルのテキストを更新
    }

}
