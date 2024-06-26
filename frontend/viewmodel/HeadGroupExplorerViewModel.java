package viewmodel;

import javax.swing.JList;
import model.*;
import view.*;

public class HeadGroupExplorerViewModel {

  public HeadGroupExplorerViewModel(HeadGroupExplorer headGroupExplorer,
      DocumentsViewer documentsViewer, SceneManager sceneManager) {
    // 対象の項目に対してSelectされたとき（View）
    headGroupExplorer.enterButton.addActionListener(e -> {
      var selects = (JList<?>) headGroupExplorer.HeadGroupScrolPanel.getViewport().getView(); // リストを取得
      String selectedValue = (String) selects.getSelectedValue(); // 選択中の項目取得
      var obtainedHeadGroup = HeadsController.getHeadGroup(selectedValue).message; // ドキュメント取得

      // Headであったとき
      if (obtainedHeadGroup.headGroups.size() + obtainedHeadGroup.heads.size() == 0) {
        var obtainedDocs = DocumentController.getDocuments(selectedValue).message;
        documentsViewer.setDocuments(obtainedDocs); // ドキュメントをDocViewerにセット
        sceneManager.showPanel(SceneManager.Panel.DocumentsViewer); // 遷移
      } else { // Headではなかったとき
        headGroupExplorer.setHeadGroup(obtainedHeadGroup); // HeadGroupをExplorerにセット
        sceneManager.showPanel(SceneManager.Panel.HeadGroupExplorer); // 遷移
      }
    });
  }

}
