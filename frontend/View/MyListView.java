package view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class MyListView extends JPanel {

  public List<String> list = new ArrayList<String>();
  public List<String> groups = new ArrayList<String>();
  public JScrollPane listScrollPanel;
  public JButton selectButton = new JButton("Select");
  public JButton refreshButton = new JButton("Refresh");

  public MyListView() {
    setLayout(new BorderLayout());
    JPanel bottomPanel = new JPanel(new BorderLayout());
    JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
    buttonPanel.add(selectButton);
    buttonPanel.add(refreshButton);
    bottomPanel.add(buttonPanel, BorderLayout.CENTER);
    add(bottomPanel, BorderLayout.SOUTH);
  }

  // マイリストの更新
  public void updateMyList(String[] list, Function<String, CellRenderer.CellType> cellType) {
    if (listScrollPanel != null)
      remove(listScrollPanel);
    this.list = Arrays.asList(list);

    JList<String> jlist = new JList<>(list);
    // リストに特徴を付ける
    jlist.setCellRenderer(new CellRenderer(cellType));

    add(listScrollPanel = new JScrollPane(jlist), BorderLayout.CENTER);
    revalidate();
    repaint();
  }
}
