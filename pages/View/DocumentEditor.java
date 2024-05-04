import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DocumentEditor extends JPanel {
    private Document document;
    private JTextField titleField;
    private JTextArea contentArea;
    private JButton saveButton;
    private JButton deleteButton;
    private JButton viewButton;
    private SceneManager sceneManager;

    public DocumentEditor(Document document, SceneManager sceneManager) {
        this.document = document;
        this.sceneManager = sceneManager;

        setLayout(new BorderLayout());

        titleField = new JTextField(document.getTitle());
        add(titleField, BorderLayout.NORTH);

        contentArea = new JTextArea(document.getContent());
        add(new JScrollPane(contentArea), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 保存処理
                document.setTitle(titleField.getText());
                document.setContent(contentArea.getText());
                // 保存した後、同じHead名を持つDocumentViewerに遷移
                sceneManager.goToDocumentViewer(document.getHeadName());
            }
        });
        buttonPanel.add(saveButton);

        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 削除処理
                // 削除した後、同じHead名を持つDocumentViewerに遷移
                sceneManager.goToDocumentViewer(document.getHeadName());
            }
        });
        buttonPanel.add(deleteButton);

        viewButton = new JButton("View");
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 同じHead名を持つDocumentViewerに遷移
                sceneManager.goToDocumentViewer(document.getHeadName());
            }
        });
        buttonPanel.add(viewButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }
}
