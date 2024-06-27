package viewmodel;

import javax.swing.JList;
import view.*;
import model.*;
import java.util.ArrayList;

public class MyListViewVM {
  ArrayList<Document> liked;
  ArrayList<Document> myDocument;
  ArrayList<String> myGroups;

  public MyListViewVM(MyListView myListView, DocumentsViewer documentsViewer,
      DocumentEditor documentEditor, HeadGroupExplorer headGroupExplorer,
      SceneManager sceneManager) {
    // ２秒おきにサーバーからドキュメント取得
    myListView.refreshButton.addActionListener(e -> {
      refresh(myListView);
    });
    refresh(myListView);

    // 対象の項目に対してSelectされたとき（View）
    myListView.selectButton.addActionListener(e ->

    {
      var selects = (JList<?>) myListView.listScrollPanel.getViewport().getView(); // リストを取得
      String selectedValue = (String) selects.getSelectedValue(); // 選択中の項目取得

      if (liked.stream().map(d -> d.head).toList().contains(selectedValue)) {
        var documents = DocumentController.getFromHead(selectedValue);
        if (documents.hasError()) {
          return;
        }
        var docs = documents.message;
        var my = liked.stream().filter(d -> d.head.equals(selectedValue)).findFirst().get();
        var idx = docs.stream().map((d) -> d.docID).toList().indexOf(my.docID);
        documentsViewer.setDocuments(docs);
        documentsViewer.setOffset(idx);
        sceneManager.showPanel(SceneManager.Panel.DocumentsViewer);
      } else if (myDocument.stream().map(d -> d.head).toList()
          .contains(selectedValue.split("\t")[0])) {
        var doc = myDocument.stream().filter(d -> d.head.equals(selectedValue.split("\t")[0]))
            .findFirst().get();
        documentEditor.setDocument(doc);
        sceneManager.showPanel(SceneManager.Panel.DocumentEditor);
      } else if (myGroups.contains(selectedValue)) {
        var groups = HeadsController.getHeadGroup(selectedValue);
        if (groups.hasError()) {
          return;
        }
        var group = groups.message;
        headGroupExplorer.setHeadGroup(group);
        sceneManager.showPanel(SceneManager.Panel.HeadGroupExplorer);
      }
    });
  }

  private void refresh(MyListView myListView) {
    var likedRes = DocumentController.getLiked();
    var myDocumentRes = DocumentController.getMyDocuments();
    var myGroupsRes = HeadsController.getMyHeadGroups();
    if (likedRes.hasError(Response.INVALID_VALUE) || myDocumentRes.hasError(Response.INVALID_VALUE)
        || myGroupsRes.hasError(Response.INVALID_VALUE)) {
      System.out.println("Error" + "\tliked" + likedRes.error + "\tmy" + myDocumentRes.error
          + "\tgroup" + myGroupsRes.error);
      return;
    }
    liked = likedRes.hasError(Response.NOT_FOUND) ? new ArrayList<>() : likedRes.message;
    myDocument =
        myDocumentRes.hasError(Response.NOT_FOUND) ? new ArrayList<>() : myDocumentRes.message;
    myGroups = myGroupsRes.hasError(Response.NOT_FOUND) ? new ArrayList<>() : myGroupsRes.message;
    var concat = new ArrayList<String>();
    concat.addAll(new ArrayList<>(myDocument).stream()
        .map(d -> d.head + "\t" + d.content.length() + "文字").toList());
    concat.addAll(new ArrayList<>(liked).stream().map(d -> d.head).toList());
    concat.addAll(myGroups);
    myListView.updateMyList(concat.toArray(new String[0]), (String s) -> {
      var docStrings = myDocument.stream().map(d -> d.head).toList();
      var likedStrings = liked.stream().map(d -> d.head).toList();
      if (docStrings.contains(s.split("\t")[0])) {
        return CellRenderer.CellType.DOCUMENT;
      } else if (likedStrings.contains(s)) {
        return CellRenderer.CellType.LIKED_DOCUMENT;
      } else {
        return CellRenderer.CellType.HEAD_GROUP;
      }
    });
  }
}
