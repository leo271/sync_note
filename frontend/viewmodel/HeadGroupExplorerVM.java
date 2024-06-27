package viewmodel;

import java.util.ArrayList;
import javax.swing.JList;
import javax.swing.JOptionPane;
import model.*;
import view.*;
import view.SceneManager.Panel;

public class HeadGroupExplorerVM {
  public HeadGroupExplorerVM(HeadGroupExplorer headGroupExplorer, DocumentsViewer documentsViewer,
      DocumentEditor documentEditor, SceneManager sceneManager) {
    setHeadGroup("root", headGroupExplorer, "REPLACE");
    // 対象の項目に対してSelectされたとき（View）
    headGroupExplorer.enterButton.addActionListener(e -> {
      var selects = (JList<?>) headGroupExplorer.HeadGroupScrolPanel.getViewport().getView(); // リストを取得
      String selectedValue = (String) selects.getSelectedValue(); // 選択中の項目取得
      var isHeadGroup = headGroupExplorer.headGroup.headGroups.contains(selectedValue);
      System.out.println("isHeadGroup: " + isHeadGroup + " selectedValue: " + selectedValue);
      // HeadGroupの時
      if (isHeadGroup) {
        setHeadGroup(selectedValue, headGroupExplorer, "TRACE");// ドキュメントをDocViewerにセット
      } else { // Headの時
        var documentsRes = DocumentController.getFromHead(selectedValue);
        if (documentsRes.hasError(Response.INVALID_VALUE))
          return;
        ArrayList<Document> documents =
            documentsRes.hasError(Response.NOT_FOUND) ? new ArrayList<>() : documentsRes.message;
        documentsViewer.setDocuments(documents);
        sceneManager.showPanel(Panel.DocumentsViewer);
      }
    });

    // 対象の項目に対してSelectされたとき（View）
    headGroupExplorer.backButton.addActionListener(e -> {
      if (headGroupExplorer.prevHeadGroups.isEmpty()) {
        JOptionPane.showMessageDialog(null, "これ以上戻れません", "Error", JOptionPane.ERROR_MESSAGE);
      }
      var prev = headGroupExplorer.prevHeadGroups.pop();
      setHeadGroup(prev, headGroupExplorer, "BACK");// ドキュメントをDocViewerにセット
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
      headGroup.headGroups.add(newHeadName);
      res = HeadsController.updateHeadGroup(headGroup);
      headGroupExplorer.setHeadGroup(headGroup);
    });

    headGroupExplorer.addDocumentButton.addActionListener(e -> {
      var selects = (JList<?>) headGroupExplorer.HeadGroupScrolPanel.getViewport().getView(); // リストを取得
      String selectedValue = (String) selects.getSelectedValue(); // 選択中の項目取得
      var isHead = headGroupExplorer.headGroup.heads.contains(selectedValue);
      if (!isHead)
        JOptionPane.showMessageDialog(null, "Headを選択してください", "Error", JOptionPane.ERROR_MESSAGE);
      var res = DocumentController.create(selectedValue);
      if (res.hasError())
        return;

      documentEditor.setDocument(res.message);
      sceneManager.showPanel(Panel.DocumentEditor);
    });

    headGroupExplorer.deleteButton.addActionListener(e -> {
      var selects = (JList<?>) headGroupExplorer.HeadGroupScrolPanel.getViewport().getView(); // リストを取得
      String selectedValue = (String) selects.getSelectedValue(); // 選択中の項目取得
      var isHead = headGroupExplorer.headGroup.heads.contains(selectedValue);
      if (isHead) {

      } else {

      }
    });
  }

  public void setHeadGroup(String name, HeadGroupExplorer headGroupExplorer, String mode) {
    var headGroupRes = HeadsController.getHeadGroup(name);
    if (headGroupRes.hasError(Response.INVALID_VALUE)) {
      System.out.println("Error: " + headGroupRes.error);
      return;
    }
    HeadGroup headGroup =
        headGroupRes.hasError(Response.NOT_FOUND) ? new HeadGroup(name) : headGroupRes.message;

    switch (mode) {
      case "REPLACE":
        headGroupExplorer.prevHeadGroups.clear();
        break;
      case "TRACE":
        headGroupExplorer.prevHeadGroups.push(headGroupExplorer.headGroup.name);
        break;
      case "BACK":
        break;
      default:
        break;
    }
    headGroupExplorer.setHeadGroup(headGroup);// ドキュメントをDocViewerにセット
  }
}
