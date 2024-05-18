package View;

import java.util.List;
import javax.swing.JPanel;
import Model.Data.Head;
import Model.Data.HeadGroup;

public class HeadGroupExplorer extends JPanel {
    public List<HeadGroup> headGroups;
    public SearchView searchView;

    // 指定されたHeadGroup内の要素を表示するメソッド
    public void showHead(HeadGroup group) {
        System.out.println("Head " + group.name + ":");
        for (Head head : group.heads) {
            System.out.println("- " + head.name);
        }
    }

    // 指定されたHeadGroupを削除するメソッド
    public void deleteHeadGroup(HeadGroup group) {
        headGroups.remove(group);
        System.out.println("Deleted HeadGroup: " + group.name);
    }

    // HeadGroupに新しい要素を追加するメソッド
    public void addHeadToHeadGroup(String name, HeadGroup group) {
        // 作成したHeadを取得して追加する
        List<Head> results = searchView.searchHeadsByName(name);
        if (!results.isEmpty()) {
            Head newHead = results.get(0);
            group.addHead(newHead);
            System.out.println("Added " + newHead.name + " to " + group.name);
        } else {
            System.out.println("Head not found: " + name);
        }
    }
}
