package viewmodel;

import javax.swing.JList;
import view.*;
import view.CellRenderer.CellType;
import model.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MyListViewVM {
  public MyListViewVM(MyListView myListView, DocumentsViewer documentsViewer,
      DocumentEditor documentEditor, HeadGroupExplorer headGroupExplorer,
      SceneManager sceneManager) {
    // リフレッシュボタンで再読み込み
    myListView.refreshButton.addActionListener(e -> {
      refresh(myListView);
    });
    refresh(myListView);

    // Deleteボタンが押されたとき
    myListView.deleteButton.addActionListener(e -> {
      var selects = (JList<?>) myListView.myDocumentsScrollPanel.getViewport().getView();
      var selectedValue = ((String) selects.getSelectedValue()).split("\t")[0];
      var myDocs = getMyDocuments();
      if (myDocs == null)
        return;
      if (myDocs.stream().map(d -> d.head).toList().contains(selectedValue)) {
        var doc = myDocs.stream().filter(d -> d.head.equals(selectedValue)).findFirst().get();
        DocumentController.delete(doc.docID);
      }
      refresh(myListView);
    });

    // Unlikeボタンが押されたとき
    myListView.unlikeButton.addActionListener(e -> {
      var selects = (JList<?>) myListView.likedDocumentsScrollPanel.getViewport().getView();
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
      refresh(myListView);
    });

    // 対象の項目に対してSelectされたとき（View）
    myListView.selectButton.addActionListener(e -> {
      String selectedValue = myListView.getSelectedValue();
      if (selectedValue == null)
        return;
      var liked = getLiked();
      var myDocument = getMyDocuments();
      var myGroups = getMyGroups();
      if (liked != null && liked.stream().map(d -> d.head).toList().contains(selectedValue)) {
        var documents = DocumentController.getFromHead(selectedValue);
        if (documents.hasError()) {
          return;
        }
        var docs = documents.message;
        var my = liked.stream().filter(d -> d.head.equals(selectedValue)).findFirst().get();
        var idx = docs.stream().map((d) -> d.docID).toList().indexOf(my.docID);
        documentsViewer.setDocuments(selectedValue, docs);
        documentsViewer.setOffset(idx);
        sceneManager.showPanel(SceneManager.Panel.DocumentsViewer);
      } else if (myDocument != null
          && myDocument.stream().map(d -> d.head).toList().contains(selectedValue.split("\t")[0])) {
        var doc = myDocument.stream().filter(d -> d.head.equals(selectedValue.split("\t")[0]))
            .findFirst().get();
        documentEditor.setDocument(doc);
        sceneManager.showPanel(SceneManager.Panel.DocumentEditor);
      } else if (myGroups != null && myGroups.contains(selectedValue)) {
        var groups = HeadsController.getHeadGroup(selectedValue);
        if (groups.hasError()) {
          return;
        }
        var group = groups.message;
        headGroupExplorer.setHeadGroup(group);
        sceneManager.showPanel(SceneManager.Panel.HeadGroupExplorer);
      }
    });

    // パネル内のクリックイベントを処理
    myListView.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        updateButtonPanel(myListView, null);
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

  private void refresh(MyListView myListView) {
    var likedN = getLiked();
    var myDocumentN = getMyDocuments();
    var myGroupsN = getMyGroups();

    var liked = likedN == null ? new ArrayList<Document>() : likedN;
    var myDocument = myDocumentN == null ? new ArrayList<Document>() : myDocumentN;
    var myGroups = myGroupsN == null ? new ArrayList<String>() : myGroupsN;
    var likedItems = new ArrayList<String>();
    likedItems.addAll(liked.stream().map(d -> d.head).toList());
    likedItems.addAll(myGroups);

    myListView.updateLists(myDocument.stream().map(d -> d.head).toArray(String[]::new),
        likedItems.toArray(new String[0]), (String s) -> {
          if (s.contains("\t")) {
            return CellRenderer.CellType.DOCUMENT;
          } else if (liked.stream().map(d -> d.head).toList().contains(s)) {
            return CellRenderer.CellType.LIKED_DOCUMENT;
          } else {
            return CellRenderer.CellType.HEAD_GROUP;
          }
        }, (CellType type) -> {
          updateButtonPanel(myListView, type);
          return null;
        });
  }
}
