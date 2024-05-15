import java.util.List;
import javax.swing.JPanel;
import model.data.Head;
import model.data.HeadGroup;

public class HeadGroupExplorer extends JPanel {
    public List<HeadGroup> headGroups;
    public SearchView searchView;

    public void showHead(HeadGroup group) {
        System.out.println("Head " + group.name + ":");
        for (Head head : group.heads) {
            System.out.println("- " + head.name);
        }
    }


    public void deleteHeadGroup(HeadGroup group) {
        headGroups.remove(group);
        System.out.println("Deleted HeadGroup: " + group.name);
    }


    public void addHeadToHeadGroup(String name, HeadGroup group) {

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
