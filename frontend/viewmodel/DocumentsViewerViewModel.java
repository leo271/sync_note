package viewmodel;

import view.DocumentsViewer;

public class DocumentsViewerViewModel {
  public DocumentsViewerViewModel(DocumentsViewer documentsViewer) {
    documentsViewer.likeButton.addActionListener(e -> {
      // HeadsController.likeDocument();
    });
  }

}
