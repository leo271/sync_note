package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import utility.Colors;

public class MyListView extends JPanel {
  public List<String> myDocuments = new ArrayList<>();
  public List<String> likedDocuments = new ArrayList<>();
  public JScrollPane myDocumentsScrollPanel;
  public JScrollPane likedDocumentsScrollPanel;
  public JButton selectButton = new JButton("選択");
  public JButton refreshButton = new JButton("リロード");
  public JButton deleteButton = new JButton("削除");
  public JButton unlikeButton = new JButton("ライク解除");
  public JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 0));
  public JList<String> myDocumentsList;
  public JList<String> likedDocumentsList;
  public JPanel listPanel = new JPanel(new GridLayout(1, 2, 10, 0));

  public MyListView() {
    setLayout(new BorderLayout());
    add(listPanel, BorderLayout.CENTER);

    JPanel myDocumentsPanel = createTitledListPanel("マイドキュメント");
    JPanel likedDocumentsPanel = createTitledListPanel("ライク済み");

    listPanel.add(myDocumentsPanel);
    listPanel.add(likedDocumentsPanel);

    JPanel bottomPanel = new JPanel(new BorderLayout());
    bottomPanel.add(buttonPanel, BorderLayout.CENTER);
    add(bottomPanel, BorderLayout.SOUTH);

    customizeButton(selectButton);
    customizeButton(refreshButton);
    customizeButton(deleteButton);
    customizeButton(unlikeButton);

    setBackground(Colors.lightGray);
    bottomPanel.setBackground(Colors.lightGray);
    buttonPanel.setBackground(Colors.pale);
    listPanel.setBackground(Colors.lightGray);
  }

  private JPanel createTitledListPanel(String title) {
    JPanel panel = new JPanel(new BorderLayout());
    JLabel titleLabel = new JLabel(title);
    titleLabel.setHorizontalAlignment(JLabel.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
    panel.add(titleLabel, BorderLayout.NORTH);

    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setBorder(BorderFactory.createEmptyBorder()); // ボーダーを削除
    panel.add(scrollPane, BorderLayout.CENTER);

    // Add padding between the title and the list
    panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    return panel;
  }

  private void customizeButton(JButton button) {
    button.setBackground(Colors.pale);
    button.setForeground(Color.WHITE);
    button.setFont(new Font("Arial", Font.PLAIN, 14));
    button.setFocusPainted(false);
    button.setOpaque(true);
    button.setBorderPainted(false);
    button.setBorder(
        BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Colors.pale, 1, true),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)));
    button.setCursor(new Cursor(Cursor.HAND_CURSOR));

    // Add hover effect
    button.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseEntered(MouseEvent e) {
        button.setBackground(Colors.lightRed);
      }

      @Override
      public void mouseExited(MouseEvent e) {
        button.setBackground(Colors.pale);
      }
    });
  }

  public void updateLists(String[] myDocs, String[] likedDocs,
      Function<String, CellRenderer.CellType> cellType, Runnable likedListener,
      Runnable myListener) {

    if (myDocumentsList != null) {
      myDocumentsList.clearSelection();
    }
    if (likedDocumentsList != null) {
      likedDocumentsList.clearSelection();
    }

    this.myDocuments = Arrays.asList(myDocs);
    this.likedDocuments = Arrays.asList(likedDocs);

    this.myDocumentsList =
        toJList(myDocuments, cellType, myListener, "マイドキュメント", myDocumentsScrollPanel);
    this.likedDocumentsList =
        toJList(likedDocuments, cellType, likedListener, "ライク済み", likedDocumentsScrollPanel);

    myDocumentsScrollPanel = new JScrollPane(myDocumentsList);
    myDocumentsScrollPanel.setBorder(BorderFactory.createEmptyBorder()); // ボーダーを削除
    likedDocumentsScrollPanel = new JScrollPane(likedDocumentsList);
    likedDocumentsScrollPanel.setBorder(BorderFactory.createEmptyBorder()); // ボーダーを削除

    listPanel.removeAll();

    JPanel myDocumentsPanel = createTitledListPanel("マイドキュメント");
    myDocumentsPanel.add(myDocumentsScrollPanel, BorderLayout.CENTER);
    JPanel likedDocumentsPanel = createTitledListPanel("ライク済み");
    likedDocumentsPanel.add(likedDocumentsScrollPanel, BorderLayout.CENTER);

    listPanel.add(myDocumentsPanel);
    listPanel.add(likedDocumentsPanel);

    revalidate();
    repaint();
  }

  private JList<String> toJList(List<String> items,
      Function<String, CellRenderer.CellType> cellType, Runnable listener, String title,
      JScrollPane scrollPane) {
    JList<String> jlist = new JList<>(items.toArray(new String[0]));
    jlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    jlist.addListSelectionListener(e -> {
      if (e.getValueIsAdjusting() || jlist.getSelectedValue() == null)
        return;
      listener.run();
    });

    jlist.setCellRenderer(new CellRenderer(cellType));
    jlist.setFixedCellHeight(50);
    jlist.setBackground(Colors.lightGray);
    jlist.setForeground(Color.BLACK);
    jlist.setBorder(BorderFactory.createEmptyBorder());

    return jlist;
  }

  public String selectedValue() {
    if (myDocumentsList.getSelectedValue() != null) {
      return myDocumentsList.getSelectedValue();
    } else if (likedDocumentsList.getSelectedValue() != null) {
      return likedDocumentsList.getSelectedValue();
    }
    return null;
  }
}
