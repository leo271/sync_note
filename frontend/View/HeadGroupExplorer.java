package view;

import javax.swing.*;
import model.HeadGroup;
import java.awt.*;
import java.util.List;
import java.util.HashSet;
import java.util.stream.Stream;

public class HeadGroupExplorer extends JPanel {

  // ダミーデータ
  public static final List<String> testListHeads = List.of("Mathematics", "Physics", "Chemistry",
      "Biology", "Computer Science", "Economics", "Psychology", "Internet");
  public static final List<String> testListHeadGroups = List.of("Java1", "Java2", "Java3",
      "Sociology", "Political Science", "History", "Philosophy", "zzz");
  public static final HeadGroup testHeadGroup =
      new HeadGroup("Java", new HashSet<>(testListHeads), new HashSet<>(testListHeadGroups), 100);
  public static final List<String> testListHeads2 =
      List.of("Tokyo", "Fukuoka", "Nagasaki", "Osaka", "Hokkaido", "Nagoya", "Kyoto", "Kumamoto");
  public static final List<String> testListHeadGroups2 = List.of("Korea", "China", "U.S.");
  public static final HeadGroup testHeadGroup2 = new HeadGroup("Countries",
      new HashSet<>(testListHeads2), new HashSet<>(testListHeadGroups2), 100);

  private JLabel titleLabel;
  public HeadGroup headGroup;
  public JScrollPane HeadGroupScrolPanel;
  public JButton enterButton = new JButton("Enter");
  public JButton addButton = new JButton("Add Head");
  public JButton deleteButton = new JButton("Delete Element");
  public JButton demoRandomButton = new JButton("Demo Switch"); // TODO: 画面遷移テスト用のボタン。本番では消す。


  public HeadGroupExplorer() {
    setLayout(new BorderLayout());
    this.headGroup = testHeadGroup; // ダミーで初期化
    refreshHeadGroup();

    JPanel bottomPanel = new JPanel(new BorderLayout());
    JPanel buttonPanel = new JPanel(new GridLayout(1, 3));

    // deleteButtonのテキストを赤くする
    deleteButton.setForeground(Color.RED);
    demoRandomButton.setAction(new AbstractAction("Demo Switch") {
      @Override
      public void actionPerformed(java.awt.event.ActionEvent e) {
        if (headGroup.equals(testHeadGroup2))
          setHeadGroup(testHeadGroup);
        else
          setHeadGroup(testHeadGroup2);
      }
    });


    buttonPanel.add(enterButton);
    buttonPanel.add(addButton);
    buttonPanel.add(deleteButton);
    buttonPanel.add(demoRandomButton);
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
