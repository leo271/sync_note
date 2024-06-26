package viewmodel;

import view.*;
import model.*;

public class SearchViewViewModel {

  public SearchViewViewModel(SearchView searchView, DocumentsViewer documentsViewer,
      HeadGroupExplorer headGroupExplorer, SceneManager sceneManager) {
    searchView.searchButton.addActionListener(e -> {
      var inputWords = searchView.searchField.getText();

    });
  }
}
