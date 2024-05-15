import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import model.data.Head;
import model.data.HeadGroup;

public class SearchView extends JPanel {
    public List<Head> heads;
    public List<HeadGroup> headGroups;
    public List<Head> myList;


    public List<Head> searchHeadsByName(String name) {
        List<Head> results = new ArrayList<>();
        for (Head head : heads) {
            if (head.name.equalsIgnoreCase(name)) {
                results.add(head);
            }
        }
        return results;
    }

   
    public List<HeadGroup> searchHeadGroupsByName(String name) {
        List<HeadGroup> results = new ArrayList<>();
        for (HeadGroup group : headGroups) {
            if (group.name.equalsIgnoreCase(name)) {
                results.add(group);
            }
        }
        return results;
    }

    // マイリストにHeadを追�?するメソ�?�?
    public void addToMyList(Head head) {
        myList.add(head);
    }

    // HeadGroupExplorerに遷移するメソ�?�?
    public void goToHeadGroupExplorer(HeadGroup group) {
        // 何かしらの遷移処�?
        System.out.println("Navigating to HeadGroupExplorer: " + group.name);
    }

    // DocumentViewerに遷移するメソ�?�?
    public void goToDocumentViewer(Head head) {
        // 何かしらの遷移処�?
        System.out.println("Navigating to DocumentViewer for: " + head.name);
    }
}
