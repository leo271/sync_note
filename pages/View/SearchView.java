import java.util.ArrayList;
import java.util.List;

public class SearchView {
    private List<Head> heads;
    private List<HeadGroup> headGroups;
    private List<Head> myList;

    public SearchView(List<Head> heads, List<HeadGroup> headGroups, List<Head> myList) {
        this.heads = heads;
        this.headGroups = headGroups;
        this.myList = myList;
    }

    // Headを名前で検索するメソッド
    public List<Head> searchHeadsByName(String name) {
        List<Head> results = new ArrayList<>();
        for (Head head : heads) {
            if (head.getName().equalsIgnoreCase(name)) {
                results.add(head);
            }
        }
        return results;
    }

    // HeadGroupを名前で検索するメソッド
    public List<HeadGroup> searchHeadGroupsByName(String name) {
        List<HeadGroup> results = new ArrayList<>();
        for (HeadGroup group : headGroups) {
            if (group.getName().equalsIgnoreCase(name)) {
                results.add(group);
            }
        }
        return results;
    }

    // マイリストにHeadを追加するメソッド
    public void addToMyList(Head head) {
        myList.add(head);
    }

    // HeadGroupExplorerに遷移するメソッド
    public void goToHeadGroupExplorer(HeadGroup group) {
        // 何かしらの遷移処理
        System.out.println("Navigating to HeadGroupExplorer: " + group.getName());
    }

    // DocumentViewerに遷移するメソッド
    public void goToDocumentViewer(Head head) {
        // 何かしらの遷移処理
        System.out.println("Navigating to DocumentViewer for: " + head.getName());
    }
}
