package view;

import javax.swing.*;
import model.HeadGroup;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Stack;
import java.util.stream.Stream;
import utility.Colors;

public class HeadGroupExplorer extends JPanel {
  private JLabel titleLabel;
  public HeadGroup headGroup = new HeadGroup("root");
  public Stack<String> prevHeadGroups = new Stack<>();
  public JScrollPane HeadGroupScrolPanel;
  public JButton backButton = new JButton("戻る");
  public JButton enterButton = new JButton("進む");
  public JButton addDocumentButton = new JButton("ドキュメントを追加");
  public JButton addHeadButton = new JButton("ヘッドを追加");
  public JButton addGroupButton = new JButton("グループを追加");
  public JButton deleteButton = new JButton("選択項目を削除");
  public JPanel buttonPanel = new JPanel(new GridLayout(1, 6, 10, 0));

  public HeadGroupExplorer() {
    setLayout(new BorderLayout());
    refreshHeadGroup();

    JPanel bottomPanel = new JPanel(new BorderLayout());
    bottomPanel.add(buttonPanel, BorderLayout.CENTER);
    add(bottomPanel, BorderLayout.SOUTH);

    // Customize buttons
    customizeButton(backButton);
    customizeButton(enterButton);
    customizeButton(addDocumentButton);
    customizeButton(addHeadButton);
    customizeButton(addGroupButton);
    customizeButton(deleteButton);

    // Add buttons to button panel
    buttonPanel.add(backButton);
    buttonPanel.add(enterButton);
    buttonPanel.add(addDocumentButton);
    buttonPanel.add(addHeadButton);
    buttonPanel.add(addGroupButton);
    buttonPanel.add(deleteButton);

    // Set background colors
    setBackground(Colors.lightGray); // Main background
    bottomPanel.setBackground(Colors.lightGray);
    buttonPanel.setBackground(Colors.pale); // Button panel background
  }

  // Customize buttons with a modern style
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

  // Update the HeadGroup
  private void refreshHeadGroup() {
    if (HeadGroupScrolPanel != null) // Remove existing list if present
      remove(HeadGroupScrolPanel);

    JList<String> list = new JList<>(Stream
        .concat(headGroup.headGroups.stream(), headGroup.heads.stream()).toArray(String[]::new));

    // Customize the list
    list.setCellRenderer(new CellRenderer(
        (String group) -> headGroup.headGroups.contains(group) ? CellRenderer.CellType.HEAD_GROUP
            : CellRenderer.CellType.HEAD));
    list.setFixedCellHeight(50); // Increase the height of each tile
    list.setBackground(Colors.lightGray);
    list.setForeground(Color.BLACK);
    list.setBorder(BorderFactory.createEmptyBorder()); // Remove the border

    HeadGroupScrolPanel = new JScrollPane(list);
    HeadGroupScrolPanel.setBorder(BorderFactory.createEmptyBorder()); // Remove the border
    add(HeadGroupScrolPanel, BorderLayout.CENTER);

    if (titleLabel != null)
      remove(titleLabel); // Remove existing title label if present
    titleLabel = new JLabel(headGroup.name, SwingConstants.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Increase font size
    add(titleLabel, BorderLayout.NORTH); // Add title label at the top
  }

  // Set and redraw
  public void setHeadGroup(HeadGroup headGroup) {
    this.headGroup = headGroup;
    refreshHeadGroup();
    revalidate();
    repaint();
  }
}
