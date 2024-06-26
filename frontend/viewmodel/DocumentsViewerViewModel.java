package viewmodel;

import view.DocumentsViewer;
import model.*;

public class DocumentsViewerViewModel {
  public DocumentsViewerViewModel(DocumentsViewer documentsViewer) {
    documentsViewer.likeButton.addActionListener(e -> {
      // HeadsController.likeDocument();
    });
  }

}
