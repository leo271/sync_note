package viewmodel;

import view.*;
import javax.swing.JOptionPane;
import model.*;

public class SearchViewVM {

  public SearchViewVM(SearchView searchView, DocumentsViewer documentsViewer,
      HeadGroupExplorer headGroupExplorer, SceneManager sceneManager) {
    // 検索
    searchView.searchButton.addActionListener(e -> {
      var inputWords = searchView.searchField.getText();
      var obtainedHeadGroup = HeadsController.searchHeads(inputWords);
      if (obtainedHeadGroup.hasError()) {
        System.out.println("Error: " + obtainedHeadGroup.message);
        if (obtainedHeadGroup.hasError(Response.NOT_FOUND))
          JOptionPane.showMessageDialog(null, "'" + inputWords + "'に一致する結果は0件でした", "Not found",
              JOptionPane.INFORMATION_MESSAGE);
        else
          JOptionPane.showMessageDialog(null, "Error: " + obtainedHeadGroup.error, "Error",
              JOptionPane.ERROR_MESSAGE);
        return;
      }
      sceneManager.showPanel(SceneManager.Panel.HeadGroupExplorer);
      headGroupExplorer.setHeadGroup(obtainedHeadGroup.message);
    });
  }
}
