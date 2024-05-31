package view;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import model.Head;
import model.HeadGroup;

public class SearchView extends JPanel {
    public List<Head> heads;
    public List<HeadGroup> headGroups;
    public List<Head> myList;

    // Headを名前で検索するメソッド
    public List<Head> searchHeadsByName(String name) {
        List<Head> results = new ArrayList<>();
        for (Head head : heads) {
            if (head.name.equalsIgnoreCase(name)) {
                results.add(head);
            }
        }
        return results;
    }

    // HeadGroupを名前で検索するメソッド
    public List<HeadGroup> searchHeadGroupsByName(String name) {
        List<HeadGroup> results = new ArrayList<>();
        for (HeadGroup group : headGroups) {
            if (group.name.equalsIgnoreCase(name)) {
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
        System.out.println("Navigating to HeadGroupExplorer: " + group.name);
    }

    // DocumentViewerに遷移するメソッド
    public void goToDocumentViewer(Head head) {
        // 何かしらの遷移処理
        System.out.println("Navigating to DocumentViewer for: " + head.name);
    }
}
