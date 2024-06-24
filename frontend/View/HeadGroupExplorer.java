package view;

import java.util.List;
import javax.swing.JPanel;
import model.HeadGroup;

public class HeadGroupExplorer extends JPanel {
    public List<HeadGroup> headGroups;
    public SearchView searchView;

    // 指定されたHeadGroup内の要素を表示するメソッド
    public void showHead(HeadGroup group) {
        System.out.println("Head " + group.name + ":");
        for (String head : group.heads) {
            System.out.println("- " + head);
        }
    }

    // 指定されたHeadGroupを削除するメソッド
    public void deleteHeadGroup(HeadGroup group) {
        headGroups.remove(group);
        System.out.println("Deleted HeadGroup: " + group.name);
    }
}
