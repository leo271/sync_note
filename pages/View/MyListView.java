import javax.swing.*;

import model.data.Head;
import model.data.HeadGroup;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MyListView extends JPanel {
    private static final List<String> testList = List.of("Apple", "Banana1", "Banana2", "Dog", "Eagle", "Fahrenheit1",
            "Fahrenheit2", "Internet", "Java1", "Java2", "Java3", "KONAMI1", "KONAMI2", "KONAMI3", "KONAMI4", "zzz");
    private List<String> myList;
    private JScrollPane myListScrollPanel;
    private JButton selectButton = new JButton("Select");

    public MyListView(/* List<Head> myList, SceneManager sceneManager */) {
        this.myList = testList;// 適当に初期化
        // this.sceneManager = sceneManager;

        this.setSize(getPreferredSize());
        setLayout(new BorderLayout());

        // マイリストをリスト形式で表示
        JList<String> list = new JList<>(myList.toArray(new String[0]));
        add(myListScrollPanel = new JScrollPane(list), BorderLayout.CENTER);

        // 動作確認
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // HeadGroupExplorerへの画面遷移処理がないため、その旨を出力
                System.out.println("Transitioning to Next");
            }
        });
        add(selectButton, BorderLayout.SOUTH);
    }

    // マイリストの更新
    public void updateMyList(Head[] heads, HeadGroup[] headGroups) {
        String[] headTitles = new String[100];

        JList<String> list = new JList<>(myList.toArray(new String[0]));
        myListScrollPanel = new JScrollPane(list);
    }

    public JButton getSelectButton() {
        return selectButton;
    }

}
