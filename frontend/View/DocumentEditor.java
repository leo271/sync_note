package view;

import javax.swing.*;
import model.Document;
import java.awt.*;

public class DocumentEditor extends JPanel {
  public JTextArea contentArea;
  public JButton saveButton;
  public JButton deleteButton;
  public JButton viewButton;


  public DocumentEditor(Document document) {
    setLayout(new BorderLayout());

    contentArea = new JTextArea(document.content);
    add(new JScrollPane(contentArea), BorderLayout.CENTER);

    JPanel buttonPanel = new JPanel();
    saveButton = new JButton("Save");
    buttonPanel.add(saveButton);

    deleteButton = new JButton("Delete");
    buttonPanel.add(deleteButton);

    viewButton = new JButton("View");
    buttonPanel.add(viewButton);

    add(buttonPanel, BorderLayout.SOUTH);
  }
}
