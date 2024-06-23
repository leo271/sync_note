import model.Document;
import model.Head;
import model.HeadGroup;
import view.Header;
import view.MyListView;
import view.SearchView;
import viewmodel.SceneTransitionVM;
import view.HeadGroupExplorer;
import view.DocumentsViewer;
import view.DocumentEditor;
import view.SceneManager;

public class Main {
  @SuppressWarnings("unused") // VMはインスタンスを作るだけなので警告を消す
  public static void main(String[] args) {
    // Create and set up the window.
    Header header = new Header();
    MyListView myListView = new MyListView(new Head[0], new HeadGroup[0]);
    SearchView searchView = new SearchView();
    HeadGroupExplorer headGroupExplorer = new HeadGroupExplorer(new Head[0], new HeadGroup[0]);
    DocumentsViewer documentsViewer = new DocumentsViewer();
    DocumentEditor documentEditor = new DocumentEditor(new Document(null, null));
    SceneManager frame = new SceneManager(header, myListView, searchView, headGroupExplorer,
        documentsViewer, documentEditor);
    frame.setSize(800, 600);

    // VM
    var sceneTransitionVM = new SceneTransitionVM(header, frame);


    // Display the window.
    frame.setVisible(true);
  }
}
