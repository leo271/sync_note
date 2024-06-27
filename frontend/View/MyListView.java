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
  public JButton selectButton = new JButton("Select");
  public JButton refreshButton = new JButton("Refresh");
  public JButton deleteButton = new JButton("Delete");
  public JButton unlikeButton = new JButton("Unlike");
  public JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 0));

  public MyListView() {
    setLayout(new BorderLayout());

    JPanel listsPanel = new JPanel(new GridLayout(1, 2, 10, 0));
    add(listsPanel, BorderLayout.CENTER);

    JPanel myDocumentsPanel = createTitledListPanel("マイドキュメント");
    JPanel likedDocumentsPanel = createTitledListPanel("ライク済み");

    listsPanel.add(myDocumentsPanel);
    listsPanel.add(likedDocumentsPanel);

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
    listsPanel.setBackground(Colors.lightGray);
  }

  private JPanel createTitledListPanel(String title) {
    JPanel panel = new JPanel(new BorderLayout());
    JLabel titleLabel = new JLabel(title);
    titleLabel.setHorizontalAlignment(JLabel.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
    panel.add(titleLabel, BorderLayout.NORTH);
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
      Function<String, CellRenderer.CellType> cellType,
      Function<CellRenderer.CellType, Void> listener) {
    this.myDocuments = Arrays.asList(myDocs);
    this.likedDocuments = Arrays.asList(likedDocs);

    updateList(myDocuments, cellType, listener, "マイドキュメント", myDocumentsScrollPanel);
    updateList(likedDocuments, cellType, listener, "ライク済み", likedDocumentsScrollPanel);

    revalidate();
    repaint();
  }

  private void updateList(List<String> items, Function<String, CellRenderer.CellType> cellType,
      Function<CellRenderer.CellType, Void> listener, String title, JScrollPane scrollPane) {
    JPanel parentPanel = (JPanel) getComponent(0);
    JPanel listPanel = (JPanel) (title.equals("マイドキュメント") ? parentPanel.getComponent(0)
        : parentPanel.getComponent(1));

    if (scrollPane != null) {
      listPanel.remove(scrollPane);
    }

    JList<String> jlist = new JList<>(items.toArray(new String[0]));
    jlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    jlist.addListSelectionListener(e -> {
      if (e.getValueIsAdjusting())
        return;
      listener.apply(cellType.apply(jlist.getSelectedValue()));
    });

    jlist.setCellRenderer(new CellRenderer(cellType));
    jlist.setFixedCellHeight(50);
    jlist.setBackground(Colors.lightGray);
    jlist.setForeground(Color.BLACK);
    jlist.setBorder(BorderFactory.createEmptyBorder());

    JScrollPane newScrollPane = new JScrollPane(jlist);
    newScrollPane.setBorder(BorderFactory.createEmptyBorder());
    listPanel.add(newScrollPane, BorderLayout.CENTER);

    if (title.equals("マイドキュメント")) {
      myDocumentsScrollPanel = newScrollPane;
    } else {
      likedDocumentsScrollPanel = newScrollPane;
    }
  }
}
