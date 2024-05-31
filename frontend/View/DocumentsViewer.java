package view;

import javax.swing.*;
import model.Document;
import java.awt.*;
import java.util.List;

public class DocumentsViewer extends JPanel {
    public List<Document> documents;
    public JLabel titleLabel;
    public JTextArea documentsArea;
    public JButton likeButton;

    public void setDocuments(List<Document> documents) {
        this.documents = documents;

        setLayout(new BorderLayout());

        titleLabel = new JLabel("Documents for ");
        add(titleLabel, BorderLayout.NORTH);

        documentsArea = new JTextArea();
        documentsArea.setEditable(false); // 編�?不可
        add(new JScrollPane(documentsArea), BorderLayout.CENTER);
    }

    // Head名を受け取り、それに属するドキュメントを表示するメソ�?�?
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
        titleLabel.setText("♥: " + like);
    }

}
