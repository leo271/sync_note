package view;

import javax.swing.*;
import model.HeadGroup;
import java.awt.*;
import java.util.Stack;
import java.util.stream.Stream;

public class HeadGroupExplorer extends JPanel {
  private JLabel titleLabel;
  public HeadGroup headGroup = new HeadGroup("root");
  public Stack<String> prevHeadGroups = new Stack<>();
  public JScrollPane HeadGroupScrolPanel;
  public JButton backButton = new JButton("Back");
  public JButton enterButton = new JButton("Enter");
  public JButton addDocumentButton = new JButton("Add Document");
  public JButton addHeadButton = new JButton("Add Head");
  public JButton addGroupButton = new JButton("Add Group");
  public JButton deleteButton = new JButton("Delete");

  public HeadGroupExplorer() {
    setLayout(new BorderLayout());
    refreshHeadGroup();

    JPanel bottomPanel = new JPanel(new BorderLayout());
    JPanel buttonPanel = new JPanel(new GridLayout(1, 3));

    buttonPanel.add(backButton);
    buttonPanel.add(enterButton);
    buttonPanel.add(addDocumentButton);
    buttonPanel.add(addHeadButton);
    buttonPanel.add(addGroupButton);
    buttonPanel.add(deleteButton);
    bottomPanel.add(buttonPanel, BorderLayout.CENTER);
    add(bottomPanel, BorderLayout.SOUTH);
  }

  // マイリストの更新
  private void refreshHeadGroup() {
    if (HeadGroupScrolPanel != null) // 既存のリストがあれば削除
      remove(HeadGroupScrolPanel);
    JList<String> list = new JList<>(Stream
        .concat(headGroup.headGroups.stream(), headGroup.heads.stream()).toArray(String[]::new));

    // リストに特徴を付ける
    list.setCellRenderer(new CellRenderer(
        (String group) -> headGroup.headGroups.contains(group) ? CellRenderer.CellType.HEAD_GROUP
            : CellRenderer.CellType.HEAD));

    HeadGroupScrolPanel = new JScrollPane(list);
    add(HeadGroupScrolPanel, BorderLayout.CENTER);

    if (titleLabel != null)
      remove(titleLabel); // 既存のタイトルラベルを削除（オプション）
    titleLabel = new JLabel(headGroup.name, SwingConstants.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 16)); // フォントを設定（オプション）
    add(titleLabel, BorderLayout.NORTH); // パネルの上部に追加
  }

  // セットして再描画
  public void setHeadGroup(HeadGroup headGroup) {
    this.headGroup = headGroup;
    refreshHeadGroup();
    revalidate();
    repaint();
  }
}
