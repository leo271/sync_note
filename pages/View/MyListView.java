package view;

import javax.swing.*;
import model.data.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;


public class MyListView extends JPanel {
  // ダミーデータ
  public static final List<String> testList =
      List.of("Apple", "Banana1", "Banana2", "Dog", "Eagle", "Fahrenheit1", "Fahrenheit2",
          "Internet", "Java1", "Java2", "Java3", "KONAMI1", "KONAMI2", "KONAMI3", "KONAMI4", "zzz");

  public List<String> myList;
  public JScrollPane myListScrollPanel;
  public JButton selectButton = new JButton("View");
  public JButton addButton = new JButton("Add Document");

  public MyListView(Head[] initialHeads, HeadGroup[] initialHeadGroups) {

    this.myList = testList;// 適当に初期化
    // this.myList = List.of(initialHeads+initialHeadGroups); //こちらにしたい

    setLayout(new BorderLayout());

    // マイリストをリスト形式で表示
    JList<String> list = new JList<>(myList.toArray(new String[0]));
    add(myListScrollPanel = new JScrollPane(list), BorderLayout.CENTER);

    JPanel bottomPanel = new JPanel(new BorderLayout());
    JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
    buttonPanel.add(addButton);
    buttonPanel.add(selectButton);
    bottomPanel.add(buttonPanel, BorderLayout.CENTER);
    add(bottomPanel, BorderLayout.SOUTH);

  }

  // マイリストの更新
  public void updateMyList(Head[] heads, HeadGroup[] headGroups) {
    remove(myListScrollPanel);
    String[] headNames = Arrays.stream(heads).map(head -> head.name).toArray(String[]::new);
    String[] headGroupNames =
        Arrays.stream(headGroups).map(headGroup -> headGroup.name).toArray(String[]::new);
    JList<String> list = new JList<>(Stream
        .concat(Arrays.stream(headNames), Arrays.stream(headGroupNames)).toArray(String[]::new));

    myListScrollPanel = new JScrollPane(list);
    add(myListScrollPanel, BorderLayout.CENTER);
    revalidate();
    repaint();
  }


}
