package viewmodel;

import javax.swing.JList;
import model.DocumentController;
import view.HeadGroupExplorer;
import view.SceneManager;

public class HeadGroupExplorerViewModel {

  public HeadGroupExplorerViewModel(HeadGroupExplorer headGroupExplorer, SceneManager scwManager) {
    // 対象の項目に対してSelectされたとき（View）
    headGroupExplorer.enterButton.addActionListener(e -> {
      var selects = (JList<?>) headGroupExplorer.HeadGroupScrolPanel.getViewport().getView(); // リストを取得
      String selectedValue = (String) selects.getSelectedValue(); // 選択中の項目取得

      var obtainedDocs = DocumentController.getDocuments(selectedValue); // ドキュメント取得
      documentsViewer.setDocuments(obtainedDocs.message); // ドキュメントをDocViewerにセット
      sceneManager.switchPanel("documentsViewer"); // 遷移
    });
  }

}
