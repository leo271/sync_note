import model.Document;
import view.Header;
import view.MyListView;
import view.SearchView;
import viewmodel.HeadGroupExplorerViewModel;
import viewmodel.MyListViewViewModel;
import viewmodel.SceneTransitionVM;
import viewmodel.SearchViewViewModel;
import view.HeadGroupExplorer;
import view.DocumentsViewer;
import view.DocumentEditor;
import view.SceneManager;

public class Main {
  public static void main(String[] args) {
    // Create and set up the window.
    Header header = new Header();
    MyListView myListView = new MyListView(null, null);
    SearchView searchView = new SearchView();

    HeadGroupExplorer headGroupExplorer = new HeadGroupExplorer();
    DocumentsViewer documentsViewer = new DocumentsViewer(null);


    DocumentEditor documentEditor = new DocumentEditor(new Document("example head"));
    SceneManager frame = new SceneManager(header, myListView, searchView, headGroupExplorer,
        documentsViewer, documentEditor);
    frame.setSize(800, 600);

    // VM
    new SceneTransitionVM(header, frame);
    new SearchViewViewModel(searchView, documentsViewer, headGroupExplorer, frame);
    new MyListViewViewModel(myListView, documentsViewer, frame);
    new HeadGroupExplorerViewModel(headGroupExplorer, documentsViewer, frame);
    // DocumentEditorVM
    // DocumentsViewerVM

    // Display the window.
    frame.setVisible(true);
  }
}
