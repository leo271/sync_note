import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        // Create and set up the window.
        Header header = new Header();
        MyListView myListView = new MyListView();
        SearchView searchView = new SearchView();
        HeadGroupExplorer headGroupExplorer = new HeadGroupExplorer();
        DocumentsViewer documentsViewer = new DocumentsViewer();
        JFrame frame = new SceneManager(header, myListView, searchView, headGroupExplorer,
                documentsViewer);
        frame.setSize(800, 600);

        // Display the window.
        frame.setVisible(true);
    }
}
