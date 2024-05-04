import java.util.ArrayList;
import java.util.List;

public class HeadGroupExplorer {
    private List<HeadGroup> headGroups;
    private SearchView searchView;

    public HeadGroupExplorer(List<HeadGroup> headGroups, SearchView searchView) {
        this.headGroups = headGroups;
        this.searchView = searchView;
    }

    // 指定されたHeadGroup内の要素を表示するメソッド
    public void showHead(HeadGroup group) {
        System.out.println("Head " + group.getName() + ":");
        for (Head head : group.getHeads()) {
            System.out.println("- " + head.getName());
        }
    }

    // 指定されたHeadGroupを削除するメソッド
    public void deleteHeadGroup(HeadGroup group) {
        headGroups.remove(group);
        System.out.println("Deleted HeadGroup: " + group.getName());
    }

    // HeadGroupに新しい要素を追加するメソッド
    public void addHeadToHeadGroup(String name, HeadGroup group) {
        // 作成したHeadを取得して追加する
        List<Head> results = searchView.searchHeadsByName(name);
        if (!results.isEmpty()) {
            Head newHead = results.get(0);
            group.addHead(newHead);
            System.out.println("Added " + newHead.getName() + " to " + group.getName());
        } else {
            System.out.println("Head not found: " + name);
        }
    }
}
