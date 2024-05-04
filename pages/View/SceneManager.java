public class SceneManager {
    private SearchView searchView;
    private HeadGroupExplorer headGroupExplorer;
    private DocumentViewer documentViewer;

    public SceneManager(SearchView searchView, HeadGroupExplorer headGroupExplorer, DocumentViewer documentViewer) {
        this.searchView = searchView;
        this.headGroupExplorer = headGroupExplorer;
        this.documentViewer = documentViewer;
    }

    // HeadGroupExplorerに遷移するメソッド
    public void goToHeadGroupExplorer(HeadGroup group) {
        headGroupExplorer.showElements(group);
        // 何かしらの遷移処理
    }

    // DocumentViewerに遷移するメソッド
    public void goToDocumentViewer(Head head) {
        documentViewer.viewDocument(head);
        // 何かしらの遷移処理
    }

    // HeadGroupの削除メソッド
    public void deleteHeadGroup(HeadGroup group) {
        headGroupExplorer.deleteHeadGroup(group);
    }

    // HeadGroupに新しい要素を追加するメソッド
    public void addElementToHeadGroup(String name, HeadGroup group) {
        headGroupExplorer.addElementToHeadGroup(name, group);
    }
}
