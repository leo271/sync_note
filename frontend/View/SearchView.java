package view;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import model.HeadGroup;

public class SearchView extends JPanel {
    public List<String> heads;
    public List<HeadGroup> headGroups;
    public List<String> myList;

    // Headを名前で検索するメソッド
    public List<String> searchHeadsByName(String name) {
        List<String> results = new ArrayList<>();
        for (String head : heads) {
            if (head.equalsIgnoreCase(name)) {
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
    public void addToMyList(String head) {
        myList.add(head);
    }

    // HeadGroupExplorerに遷移するメソッド
    public void goToHeadGroupExplorer(HeadGroup group) {
        // 何かしらの遷移処理
        System.out.println("Navigating to HeadGroupExplorer: " + group.name);
    }

    // DocumentViewerに遷移するメソッド
    public void goToDocumentViewer(String head) {
        // 何かしらの遷移処理
        System.out.println("Navigating to DocumentViewer for: " + head);
    }
}
