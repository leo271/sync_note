package viewmodel;

import javax.swing.JList;
import view.*;
import model.*;

public class MyListViewViewModel {

  public MyListViewViewModel(MyListView myListView, DocumentsViewer documentsViewer,
      SceneManager sceneManager) {


    // Add Documentが押されたとき
    myListView.addButton.addActionListener(e -> {


    });


    // 対象の項目に対してSelectされたとき（View）
    myListView.selectButton.addActionListener(e -> {
      var selects = (JList<?>) myListView.myListScrollPanel.getViewport().getView(); // リストを取得
      String selectedValue = (String) selects.getSelectedValue(); // 選択中の項目取得
      var obtainedDocs = DocumentController.getDocuments(selectedValue); // ドキュメント取得
      documentsViewer.setDocuments(obtainedDocs.message); // ドキュメントをDocViewerにセット
      sceneManager.switchPanel("documentsViewer"); // 遷移
    });


  }


}
