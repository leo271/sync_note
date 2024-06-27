package viewmodel;

import javax.swing.JList;
import view.*;
import view.CellRenderer.CellType;
import model.*;
import java.util.ArrayList;

public class MyListViewVM {
  public MyListViewVM() {
    // リフレッシュボタンで再読み込み
    Scenes.myListView.refreshButton.addActionListener(e -> {
      refresh();
    });
    refresh();

    // Deleteボタンが押されたとき
    Scenes.myListView.deleteButton.addActionListener(e -> {
      var selects = (JList<?>) Scenes.myListView.myDocumentsScrollPanel.getViewport().getView();
      var selectedValue = ((String) selects.getSelectedValue());
      var myDocs = getMyDocuments();
      if (myDocs == null)
        return;
      if (myDocs.stream().map(d -> d.head).toList().contains(selectedValue)) {
        var doc = myDocs.stream().filter(d -> d.head.equals(selectedValue)).findFirst().get();
        DocumentController.delete(doc.docID);
      }
      refresh();
    });

    // Unlikeボタンが押されたとき
    Scenes.myListView.unlikeButton.addActionListener(e -> {
      var selects = (JList<?>) Scenes.myListView.likedDocumentsScrollPanel.getViewport().getView();
      String selectedValue = (String) selects.getSelectedValue();
      var liked = getLiked();
      var myGroups = getMyGroups();
      if (liked != null && liked.stream().map(d -> d.head).toList().contains(selectedValue)) {
        var doc = liked.stream().filter(d -> d.head.equals(selectedValue)).findFirst().get();
        DocumentController.toggleLike(doc);
      } else if (myGroups != null && myGroups.contains(selectedValue)) {
        var res = HeadsController.toggleLike(selectedValue);
        if (res.hasError()) {
          return;
        }
      }
      refresh();
    });

    // 対象の項目に対してSelectされたとき（View）
    Scenes.myListView.selectButton.addActionListener(e -> {
      var selectedValue = Scenes.myListView.selectedValue();
      if (selectedValue == null)
        return;
      var liked = getLiked();
      var myDocument = getMyDocuments();
      var myGroups = getMyGroups();
      if (liked != null && liked.stream().map(d -> d.head).toList().contains(selectedValue)) {
        // ライク済みのドキュメントの場合
        var documents = DocumentController.getFromHead(selectedValue);
        if (documents.hasError()) {
          return;
        }
        var docs = documents.message;
        var my = liked.stream().filter(d -> d.head.equals(selectedValue)).findFirst().get();
        Scenes.documentsViewer.setDocuments(selectedValue, docs, my.docID);
        Scenes.sceneManager.showPanel(SceneManager.Panel.DocumentsViewer);
      } else if (myDocument != null
          && myDocument.stream().map(d -> d.head).toList().contains(selectedValue.split("\t")[0])) {
        // 自分が書いたドキュメントの場合
        var doc = myDocument.stream().filter(d -> d.head.equals(selectedValue.split("\t")[0]))
            .findFirst().get();
        Scenes.documentEditor.setDocument(doc);
        Scenes.sceneManager.showPanel(SceneManager.Panel.DocumentEditor);
        Scenes.documentEditor.setEdit(true);
        Scenes.header.resetButtonStyles();;
      } else if (myGroups != null && myGroups.contains(selectedValue)) {
        // ライクしたHeadGroupの場合
        var groups = HeadsController.getHeadGroup(selectedValue);
        if (groups.hasError()) {
          return;
        }
        var group = groups.message;
        Scenes.headGroupExplorer.setHeadGroup(group);
        Scenes.header.explorerButton.doClick();
      }
    });
  }

  private void updateButtonPanel(MyListView myListView, CellType type) {
    myListView.buttonPanel.removeAll();
    myListView.buttonPanel.add(myListView.selectButton);
    if (type == CellType.HEAD_GROUP || type == CellType.LIKED_DOCUMENT) {
      myListView.buttonPanel.add(myListView.unlikeButton);
    } else if (type == CellType.DOCUMENT) {
      myListView.buttonPanel.add(myListView.deleteButton);
    }
    myListView.buttonPanel.add(myListView.refreshButton);
    myListView.buttonPanel.revalidate();
    myListView.buttonPanel.repaint();
  }

  private void focusChange(MyListView view, String type) {
    // print stack trace
    if (type.equals("liked")) {
      view.myDocumentsList.clearSelection();
      updateButtonPanel(view, CellType.LIKED_DOCUMENT);
    } else {
      view.likedDocumentsList.clearSelection();
      updateButtonPanel(view, CellType.DOCUMENT);
    }
  }


  private ArrayList<Document> getLiked() {
    var likedRes = DocumentController.getLiked();
    if (likedRes.hasError()) {
      System.out.println("Error" + likedRes.error);
      return null;
    }
    return likedRes.message;
  }

  private ArrayList<Document> getMyDocuments() {
    var myDocumentRes = DocumentController.getMyDocuments();
    if (myDocumentRes.hasError()) {
      System.out.println("Error" + myDocumentRes.error);
      return null;
    }
    return myDocumentRes.message;
  }

  private ArrayList<String> getMyGroups() {
    var myGroupsRes = HeadsController.getMyHeadGroups();
    if (myGroupsRes.hasError()) {
      System.out.println("Error" + myGroupsRes.error);
      return null;
    }
    return myGroupsRes.message;
  }

  public void refresh() {
    System.out.println("refresh mylist");
    var likedN = getLiked();
    var myDocumentN = getMyDocuments();
    var myGroupsN = getMyGroups();

    var liked = likedN == null ? new ArrayList<Document>() : likedN;
    var myDocument = myDocumentN == null ? new ArrayList<Document>() : myDocumentN;
    var myGroups = myGroupsN == null ? new ArrayList<String>() : myGroupsN;
    var likedItems = new ArrayList<String>();
    likedItems.addAll(liked.stream().map(d -> d.head).toList());
    likedItems.addAll(myGroups);

    Scenes.myListView.updateLists(myDocument.stream().map(d -> d.head).toArray(String[]::new),
        likedItems.toArray(new String[0]), (String s) -> {
          if (myDocument.stream().map(d -> d.head).toList().contains(s)) {
            return CellRenderer.CellType.DOCUMENT;
          } else if (liked.stream().map(d -> d.head).toList().contains(s)) {
            return CellRenderer.CellType.LIKED_DOCUMENT;
          } else {
            return CellRenderer.CellType.HEAD_GROUP;
          }
        }, () -> {
          focusChange(Scenes.myListView, "liked");
        }, () -> {
          focusChange(Scenes.myListView, "my");
        });
  }
}
