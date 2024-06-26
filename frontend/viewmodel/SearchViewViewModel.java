package viewmodel;

import view.*;
import model.*;

public class SearchViewViewModel {

  public SearchViewViewModel(SearchView searchView, DocumentsViewer documentsViewer,
      HeadGroupExplorer headGroupExplorer, SceneManager sceneManager) {

    // 検索
    searchView.searchButton.addActionListener(e -> {
      var inputWords = searchView.searchField.getText();
      var obtainedHeadGroup = HeadsController.searchHeads(inputWords);
      sceneManager.switchPanel("headGroupExplorer");
      headGroupExplorer.setHeadGroup(obtainedHeadGroup.message);
    });


  }
}
