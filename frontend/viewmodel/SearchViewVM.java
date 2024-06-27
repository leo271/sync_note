package viewmodel;

import view.*;
import model.*;
import utility.Dialog;

public class SearchViewVM {
  public SearchViewVM() {
    // 検索
    Scenes.searchView.searchButton.addActionListener(e -> {
      var inputWords = Scenes.searchView.searchField.getText();
      var obtainedHeadGroup = HeadsController.searchHeads(inputWords);
      if (obtainedHeadGroup.hasError()) {
        System.out.println("Error: " + obtainedHeadGroup.message);
        if (obtainedHeadGroup.hasError(Response.NOT_FOUND))
          Dialog.show(Scenes.searchView, "何も、、なかった、、", "'" + inputWords + "'に一致する結果は0件でした");
        else
          Dialog.show(Scenes.searchView, "エラー", "Error: " + obtainedHeadGroup.error);
        return;
      }
      Scenes.headGroupExplorer.setHeadGroup(obtainedHeadGroup.message);
      Scenes.header.explorerButton.doClick();
    });
  }
}
