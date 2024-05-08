import javax.swing.*;
import model.data.Document;
import java.awt.*;
import java.util.List;

public class DocumentsViewer extends JPanel {
    public List<Document> documents;
    public JLabel titleLabel;
    public JTextArea documentsArea;

    public void setDocuments(List<Document> documents) {
        this.documents = documents;

        setLayout(new BorderLayout());

        titleLabel = new JLabel("Documents for ");
        add(titleLabel, BorderLayout.NORTH);

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
    }
}
