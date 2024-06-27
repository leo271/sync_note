import viewmodel.HeadGroupExplorerVM;
import viewmodel.DocumentEditorVM;
import viewmodel.DocumentsViewerVM;
import viewmodel.MyListViewVM;
import viewmodel.HeaderVM;
import viewmodel.SearchViewVM;
import view.SceneManager;
import view.Scenes;

public class Main {
  public static void main(String[] args) {
    SceneManager frame = Scenes.sceneManager;
    frame.setSize(800, 600);

    // VM
    new HeaderVM();
    new SearchViewVM();
    new MyListViewVM();
    new HeadGroupExplorerVM();
    new DocumentEditorVM();
    new DocumentsViewerVM();

    // Display the window.
    frame.setVisible(true);
  }
}
