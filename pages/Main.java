import javax.swing.JFrame;
import model.data.Head;
import model.data.HeadGroup;

public class Main {
  public static void main(String[] args) {
    // Create and set up the window.
    Header header = new Header();
    MyListView myListView = new MyListView(new Head[0], new HeadGroup[0]);
    SearchView searchView = new SearchView();
    HeadGroupExplorer headGroupExplorer = new HeadGroupExplorer();
    DocumentsViewer documentsViewer = new DocumentsViewer();
    DocumentEditor documentEditor = new DocumentEditor();
    JFrame frame = new SceneManager(header, myListView, searchView, headGroupExplorer,
        documentsViewer, documentEditor);
    frame.setSize(800, 600);

    // Display the window.
    frame.setVisible(true);
  }
}
