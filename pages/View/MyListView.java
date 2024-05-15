import javax.swing.*;

import model.data.Head;
import model.data.HeadGroup;

import java.awt.*;
import java.util.List;

public class MyListView extends JPanel {
    public static final List<String> testList = List.of("Apple", "Banana1", "Banana2", "Dog",
            "Eagle", "Fahrenheit1", "Fahrenheit2", "Internet", "Java1", "Java2", "Java3", "KONAMI1",
            "KONAMI2", "KONAMI3", "KONAMI4", "zzz");
    public List<String> myList;
    public JScrollPane myListScrollPanel;
    public JButton selectButton = new JButton("Select");

    public MyListView() {
        this.myList = testList;

        setLayout(new BorderLayout());


        JList<String> list = new JList<>(myList.toArray(new String[0]));
        add(myListScrollPanel = new JScrollPane(list), BorderLayout.CENTER);
        add(selectButton, BorderLayout.SOUTH);
    }


    public void updateMyList(Head[] heads, HeadGroup[] headGroups) {
        JList<String> list = new JList<>(myList.toArray(new String[0]));
        myListScrollPanel = new JScrollPane(list);
    }

    public JButton getSelectButton() {
        return selectButton;
    }

}
