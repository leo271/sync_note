package viewmodel;

import java.util.ArrayList;
import javax.swing.JList;
import javax.swing.JOptionPane;
import model.*;
import view.*;
import view.SceneManager.Panel;

public class HeadGroupExplorerVM {
  public HeadGroupExplorerVM(HeadGroupExplorer headGroupExplorer, DocumentsViewer documentsViewer,
      SceneManager sceneManager) {
    setHeadGroup("root", headGroupExplorer);
    // 対象の項目に対してSelectされたとき（View）
    headGroupExplorer.enterButton.addActionListener(e -> {
      var selects = (JList<?>) headGroupExplorer.HeadGroupScrolPanel.getViewport().getView(); // リストを取得
      String selectedValue = (String) selects.getSelectedValue(); // 選択中の項目取得
      var isHeadGroup = headGroupExplorer.headGroup.headGroups.contains(selectedValue);

      // HeadGroupの時
      if (isHeadGroup) {
        setHeadGroup(selectedValue, headGroupExplorer);// ドキュメントをDocViewerにセット
      } else { // Headの時
        var documentsRes = DocumentController.getDocuments(selectedValue);
        if (documentsRes.hasError(Response.INVALID_VALUE))
          return;
        ArrayList<Document> documents =
            documentsRes.hasError(Response.NOT_FOUND) ? new ArrayList<>() : documentsRes.message;
        documentsViewer.setDocuments(documents);
        sceneManager.showPanel(Panel.DocumentsViewer);
      }
    });

    headGroupExplorer.addHeadButton.addActionListener(e -> {
      var newHeadName = JOptionPane.showInputDialog("ヘッド名を入力してください");
      if (newHeadName == null || newHeadName.isEmpty())
        return;
      var res = HeadsController.create(newHeadName, "H");
      if (res.hasError())
        return;
      var headGroup = headGroupExplorer.headGroup;
      headGroup.heads.add(newHeadName);
      res = HeadsController.updateHeadGroup(headGroup);
      headGroupExplorer.setHeadGroup(headGroup);
    });

    headGroupExplorer.addGroupButton.addActionListener(e -> {
      var newHeadName = JOptionPane.showInputDialog("グループ名を入力してください");
      if (newHeadName == null || newHeadName.isEmpty())
        return;
      var res = HeadsController.create(newHeadName, "G");
      if (res.hasError())
        return;
      var headGroup = headGroupExplorer.headGroup;
      headGroup.heads.add(newHeadName);
      res = HeadsController.updateHeadGroup(headGroup);
      headGroupExplorer.setHeadGroup(headGroup);
    });
  }

  public void setHeadGroup(String name, HeadGroupExplorer headGroupExplorer) {
    var headGroupRes = HeadsController.getHeadGroup(name);
    if (headGroupRes.hasError())
      return;
    HeadGroup headGroup = headGroupRes.message;
    headGroupExplorer.setHeadGroup(headGroup);// ドキュメントをDocViewerにセット
  }
}
