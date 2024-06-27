package view;

public class Scenes {
  public static final Header header = new Header();
  public static final MyListView myListView = new MyListView();
  public static final SearchView searchView = new SearchView();
  public static final HeadGroupExplorer headGroupExplorer = new HeadGroupExplorer();
  public static final DocumentsViewer documentsViewer = new DocumentsViewer();
  public static final DocumentEditor documentEditor = new DocumentEditor();
  public static final SceneManager sceneManager = new SceneManager(header, myListView, searchView,
      headGroupExplorer, documentsViewer, documentEditor);
}
