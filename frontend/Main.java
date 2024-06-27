import view.Header;
import view.MyListView;
import view.SearchView;
import viewmodel.HeadGroupExplorerVM;
import viewmodel.DocumentEditorViewModel;
import viewmodel.DocumentsViewerViewModel;
import viewmodel.MyListViewVM;
import viewmodel.HeaderVM;
import viewmodel.SearchViewViewModel;
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
    new HeaderVM(header, frame);
    new SearchViewViewModel(searchView, documentsViewer, headGroupExplorer, frame);
    new MyListViewVM(myListView, documentsViewer, documentEditor, headGroupExplorer, frame);
    new HeadGroupExplorerVM(headGroupExplorer, documentsViewer, frame);
    new DocumentEditorViewModel(documentEditor);
    new DocumentsViewerViewModel(documentsViewer);

    // Display the window.
    frame.setVisible(true);
  }
}
