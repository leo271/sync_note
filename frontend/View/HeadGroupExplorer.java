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
  public static final List<String> testList = List.of("Mathematics", "Physics", "Chemistry",
      "Biology", "Computer Science", "Economics", "Psychology", "Internet", "Java1", "Java2",
      "Java3", "Sociology", "Political Science", "History", "Philosophy", "zzz");

  private JLabel titleLabel;
  public List<String> HeadGroupList;
  public JScrollPane HeadGroupScrolPanel;
  public JButton enterButton = new JButton("Enter");
  public JButton addButton = new JButton("Add Head");
  public JButton deleteButton = new JButton("Delete Element");


  public HeadGroupExplorer(Head[] initialHeads, HeadGroup[] initialHeadGroups) {

    this.HeadGroupList = testList;// 適当に初期化
    // this.myList = List.of(initialHeads+initialHeadGroups); //こちらにしたい

    setLayout(new BorderLayout());

    titleLabel = new JLabel("Directory Name", SwingConstants.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 16)); // フォントを設定（オプション）
    add(titleLabel, BorderLayout.NORTH); // パネルの上部に追加


    // マイリストをリスト形式で表示
    JList<String> list = new JList<>(HeadGroupList.toArray(new String[0]));
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

  // マイリストの更新
  public void switchHeadGroups(Head[] heads, HeadGroup[] headGroups) {
    remove(HeadGroupScrolPanel);
    String[] headNames = Arrays.stream(heads).map(head -> head.name).toArray(String[]::new);
    String[] headGroupNames =
        Arrays.stream(headGroups).map(headGroup -> headGroup.name).toArray(String[]::new);
    JList<String> list = new JList<>(Stream
        .concat(Arrays.stream(headNames), Arrays.stream(headGroupNames)).toArray(String[]::new));

    HeadGroupScrolPanel = new JScrollPane(list);
    add(HeadGroupScrolPanel, BorderLayout.CENTER);
    revalidate();
    repaint();
  }


}
