import view.Header;
import view.MyListView;
import view.SearchView;
import viewmodel.HeadGroupExplorerViewModel;
import viewmodel.MyListViewVM;
import viewmodel.SceneTransitionVM;
import viewmodel.HeaderVM;
import view.HeadGroupExplorer;
import view.DocumentsViewer;
import view.DocumentEditor;
import view.SceneManager;

public class Main {
  public static void main(String[] args) {
    // Create and set up the window.
    Header header = new Header();
    MyListView myListView = new MyListView();
    SearchView searchView = new SearchView();

    HeadGroupExplorer headGroupExplorer = new HeadGroupExplorer();
    DocumentsViewer documentsViewer = new DocumentsViewer();


    DocumentEditor documentEditor = new DocumentEditor();
    SceneManager frame = new SceneManager(header, myListView, searchView, headGroupExplorer,
        documentsViewer, documentEditor);
    frame.setSize(800, 600);

    // VM
    new SceneTransitionVM(header, frame);
    new HeaderVM(searchView, documentsViewer, headGroupExplorer, frame);
    new MyListViewVM(myListView, documentsViewer, documentEditor, headGroupExplorer, frame);
    new HeadGroupExplorerViewModel(headGroupExplorer, documentsViewer, frame);
    // DocumentEditorVM
    // DocumentsViewerVM

    // Display the window.
    frame.setVisible(true);
  }
}
