public class SceneManager {
    private Header header;
    private MyListView myListView;
    // private SearchView searchView;
    // private HeadGroupExplorer headGroupExplorer;
    // private DocumentsViewer documentsViewer;

    public SceneManager(Header header, MyListView myListView) {
        this.header = header;
        this.myListView = myListView;
    }

    public void goToMyList() {

    }

    /*
     * // HeadGroupExplorerに遷移するメソッド
     * public void goToHeadGroupExplorer(HeadGroup group) {
     * headGroupExplorer.showElements(group);
     * // 何かしらの遷移処理
     * }
     * 
     * // DocumentViewerに遷移するメソッド
     * public void goToDocumentViewer(Head head) {
     * documentsViewer.viewDocument(head);
     * // 何かしらの遷移処理
     * }
     * 
     * // HeadGroupの削除メソッド
     * public void deleteHeadGroup(HeadGroup group) {
     * headGroupExplorer.deleteHeadGroup(group);
     * }
     * 
     * // HeadGroupに新しい要素を追加するメソッド
     * public void addElementToHeadGroup(String name, HeadGroup group) {
     * headGroupExplorer.addElementToHeadGroup(name, group);
     * }
     * 
     */
}
