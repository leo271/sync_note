package viewmodel;

import java.util.ArrayList;
import javax.swing.JList;
import model.*;
import utility.Dialog;
import view.*;
import view.SceneManager.Panel;

public class HeadGroupExplorerVM {
  public HeadGroupExplorerVM() {
    setHeadGroup("root", "REPLACE");
    // 対象の項目に対してSelectされたとき（View）
    Scenes.headGroupExplorer.enterButton.addActionListener(e -> {
      var selects = (JList<?>) Scenes.headGroupExplorer.HeadGroupScrolPanel.getViewport().getView(); // リストを取得
      String selectedValue = (String) selects.getSelectedValue(); // 選択中の項目取得
      var isHeadGroup = Scenes.headGroupExplorer.headGroup.headGroups.contains(selectedValue);
      System.out.println("isHeadGroup: " + isHeadGroup + " selectedValue: " + selectedValue);
      // HeadGroupの時
      if (isHeadGroup) {
        setHeadGroup(selectedValue, "TRACE");// ドキュメントをDocViewerにセット
      } else { // Headの時
        var documentsRes = DocumentController.getFromHead(selectedValue);
        if (documentsRes.hasError(Response.INVALID_VALUE)) {
          Dialog.show(Scenes.headGroupExplorer, "エラー", "不正な値です");
          return;
        } else if (documentsRes.hasError(Response.NOT_FOUND)) {
          Dialog.show(Scenes.headGroupExplorer, "エラー", "ドキュメントが見つかりません");
          return;
        }
        ArrayList<Document> documents = documentsRes.message;
        if (documents.isEmpty()) {
          Dialog.show(Scenes.headGroupExplorer, "探検者",
              "まだ一つもノートが書かれたことがないヘッドです。\nあなたが最初のノートを書いてみましょう！");
          return;
        }
        Scenes.header.resetButtonStyles();
        Scenes.documentsViewer.setDocuments(selectedValue, documents);
        Scenes.sceneManager.showPanel(Panel.DocumentsViewer);
      }
    });

    // 対象の項目に対してSelectされたとき（View）
    Scenes.headGroupExplorer.backButton.addActionListener(e -> {
      if (Scenes.headGroupExplorer.prevHeadGroups.isEmpty()) {
        Dialog.show(Scenes.headGroupExplorer, "注意", "これ以上戻れません");
        return;
      }
      var prev = Scenes.headGroupExplorer.prevHeadGroups.pop();
      setHeadGroup(prev, "BACK");// ドキュメントをDocViewerにセット
    });

    Scenes.headGroupExplorer.addHeadButton.addActionListener(e -> {
      var newHeadName = Dialog.input(Scenes.headGroupExplorer,
          Scenes.headGroupExplorer.headGroup.name + "にヘッドを追加します", "名前を入力してください");
      if (newHeadName == null || newHeadName.isEmpty())
        return;
      var res = HeadsController.create(newHeadName, "H");
      if (res.hasError())
        return;
      var headGroup = Scenes.headGroupExplorer.headGroup;
      headGroup.heads.add(newHeadName);
      res = HeadsController.updateHeadGroup(headGroup);
      Scenes.headGroupExplorer.setHeadGroup(headGroup);
    });

    Scenes.headGroupExplorer.addGroupButton.addActionListener(e -> {
      var newHeadName = Dialog.input(Scenes.headGroupExplorer,
          Scenes.headGroupExplorer.headGroup.name + "にヘッドグループを追加します", "名前を入力してください");
      if (newHeadName == null || newHeadName.isEmpty())
        return;
      var res = HeadsController.create(newHeadName, "G");
      if (res.hasError())
        return;
      var headGroup = Scenes.headGroupExplorer.headGroup;
      headGroup.headGroups.add(newHeadName);
      res = HeadsController.updateHeadGroup(headGroup);
      Scenes.headGroupExplorer.setHeadGroup(headGroup);
    });

    Scenes.headGroupExplorer.addDocumentButton.addActionListener(e -> {
      var selects = (JList<?>) Scenes.headGroupExplorer.HeadGroupScrolPanel.getViewport().getView(); // リストを取得
      String selectedValue = (String) selects.getSelectedValue(); // 選択中の項目取得
      var isHead = Scenes.headGroupExplorer.headGroup.heads.contains(selectedValue);
      if (!isHead)
        Dialog.show(Scenes.headGroupExplorer, "エラー", "Headを選択してください");
      var res = DocumentController.create(selectedValue);
      if (res.hasError())
        return;

      Scenes.documentEditor.setDocument(res.message);
      Scenes.sceneManager.showPanel(Panel.DocumentEditor);
    });

    Scenes.headGroupExplorer.deleteButton.addActionListener(e -> {
      var selects = (JList<?>) Scenes.headGroupExplorer.HeadGroupScrolPanel.getViewport().getView(); // リストを取得
      String selectedValue = (String) selects.getSelectedValue(); // 選択中の項目取得
      var isHead = Scenes.headGroupExplorer.headGroup.heads.contains(selectedValue);
      if (isHead) {
        var docs = DocumentController.getFromHead(selectedValue);
        if (docs.hasError(Response.INVALID_VALUE)) {
          Dialog.show(Scenes.headGroupExplorer, "エラー", "ドキュメントが見つかりません");
          return;
        }
        if (docs.message.size() > 0) {
          Dialog.show(Scenes.headGroupExplorer, "エラー", "ドキュメントが存在するHeadは削除できません");
          return;
        }
        var res = HeadsController.removeHead(selectedValue);
        if (res.hasError()) {
          System.out.println("Error: " + res.error);
          return;
        }
      } else {
        var group = HeadsController.getHeadGroup(selectedValue);
        if (group.hasError(Response.INVALID_VALUE)) {
          System.out.println("Error: " + group.error);
          return;
        }
        if (!group.hasError(Response.NOT_FOUND)
            && ((group.message.heads != null && group.message.heads.size() > 0)
                || (group.message.headGroups != null && group.message.headGroups.size() > 0))) {
          Dialog.show(Scenes.headGroupExplorer, "エラー", "HeadGroupに属するHeadが存在するため削除できません");
          return;
        }
        var res = HeadsController.removeHeadGroup(selectedValue);
        if (res.hasError()) {
          System.out.println("Error: " + res.error);
          return;
        }
      }
      var headGroup = HeadsController.getHeadGroup(Scenes.headGroupExplorer.headGroup.name).message;
      Scenes.headGroupExplorer.setHeadGroup(headGroup);
    });
  }

  public void setHeadGroup(String name, String mode) {
    var headGroupRes = HeadsController.getHeadGroup(name);
    if (headGroupRes.hasError(Response.INVALID_VALUE)) {
      System.out.println("Error: " + headGroupRes.error);
      return;
    }
    HeadGroup headGroup =
        headGroupRes.hasError(Response.NOT_FOUND) ? new HeadGroup(name) : headGroupRes.message;

    switch (mode) {
      case "REPLACE":
        Scenes.headGroupExplorer.prevHeadGroups.clear();
        break;
      case "TRACE":
        Scenes.headGroupExplorer.prevHeadGroups.push(Scenes.headGroupExplorer.headGroup.name);
        break;
      case "BACK":
        break;
      default:
        break;
    }
    Scenes.headGroupExplorer.setHeadGroup(headGroup);// ドキュメントをDocViewerにセット
  }
}
