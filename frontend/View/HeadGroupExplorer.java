package view;

import javax.swing.*;
import model.Head;
import model.HeadGroup;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class HeadGroupExplorer extends JPanel {

  // ダミーデータ
  public static final List<String> testListHeads = List.of("Mathematics", "Physics", "Chemistry",
      "Biology", "Computer Science", "Economics", "Psychology", "Internet");
  public static final List<String> testListHeadGroups = List.of("Java1", "Java2", "Java3",
      "Sociology", "Political Science", "History", "Philosophy", "zzz");



  private JLabel titleLabel;
  public List<String> HeadList;
  public List<String> HeadScrolPanel;
  public List<String> HeadGroupList;
  public JScrollPane HeadGroupScrolPanel;
  public JButton enterButton = new JButton("Enter");
  public JButton addButton = new JButton("Add Head");
  public JButton deleteButton = new JButton("Delete Element");


  public HeadGroupExplorer(String[] initialHeads, String[] initialHeadGroups) {

    this.HeadList = testListHeadGroups;
    this.HeadGroupList = testListHeads;// 適当に初期化

    // this.HeadList = List.of(initialHeads);
    // this.HeadGroupList = LIst.og(initialHeadGroups);// 適当に初期化

    setLayout(new BorderLayout());

    titleLabel = new JLabel("Directory Name", SwingConstants.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 16)); // フォントを設定（オプション）
    add(titleLabel, BorderLayout.NORTH); // パネルの上部に追加


    // リスト形式で表示
    JList<String> list = new JList<>(
        Stream.concat(HeadList.stream(), HeadGroupList.stream()).toArray(String[]::new));

    // リストに特徴をつける
    list.setCellRenderer(new HeadGroupCellRenderer(HeadGroupList));

    HeadGroupScrolPanel = new JScrollPane(list);
    add(HeadGroupScrolPanel = new JScrollPane(list), BorderLayout.CENTER);

    JPanel bottomPanel = new JPanel(new BorderLayout());
    JPanel buttonPanel = new JPanel(new GridLayout(1, 3));

    // deleteButtonのテキストを赤くする
    deleteButton.setForeground(Color.RED);

    buttonPanel.add(enterButton);
    buttonPanel.add(addButton);
    buttonPanel.add(deleteButton);
    bottomPanel.add(buttonPanel, BorderLayout.CENTER);
    add(bottomPanel, BorderLayout.SOUTH);

  }


  // リストに特徴を付ける
  // HeadとHeadGroupそれぞれにアイコンを付ける
  private class HeadGroupCellRenderer extends DefaultListCellRenderer {
    private final ImageIcon headGroupIcon =
        new ImageIcon(getClass().getResource("images/headgroup_icon.png"));
    private final ImageIcon headIcon =
        new ImageIcon(getClass().getResource("images/head_icon.png")); // アイコンリソース


    private final List<String> headGroups;

    public HeadGroupCellRenderer(List<String> headGroups) {
      this.headGroups = headGroups;
    }

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index,
        boolean isSelected, boolean cellHasFocus) {
      JLabel label =
          (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

      if (testListHeadGroups.contains(value)) {
        label.setIcon(headGroupIcon);
        label.setFont(label.getFont().deriveFont(Font.BOLD));
      } else {
        label.setIcon(headIcon);
        label.setFont(label.getFont().deriveFont(Font.PLAIN));
      }

      return label;
    }
  }



  // マイリストの更新
  public void switchHeadGroups(String[] heads, String[] headGroups) {
    remove(HeadGroupScrolPanel);
    JList<String> list = new JList<>(
        Stream.concat(Arrays.stream(heads), Arrays.stream(headGroups)).toArray(String[]::new));

    // リストに特徴を付ける
    list.setCellRenderer(new HeadGroupCellRenderer(List.of(headGroups)));

    HeadGroupScrolPanel = new JScrollPane(list);
    add(HeadGroupScrolPanel, BorderLayout.CENTER);
    revalidate();
    repaint();
  }
}
