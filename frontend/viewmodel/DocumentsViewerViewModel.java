package viewmodel;

import view.DocumentsViewer;
import model.*;

public class DocumentsViewerViewModel {
  public DocumentsViewerViewModel(DocumentsViewer documentsViewer) {

    // ライク機能
    documentsViewer.likeButton.addActionListener(e -> {
      DocumentController.likeDocument(documentsViewer.activeDocument);
    });

    // 次のDoc見るボタン
    documentsViewer.nextButton.addActionListener(e -> {
      var offset = documentsViewer.offset;
      var size = documentsViewer.documents.size();
      var off = offset - 1;
      if (off < 0)
        off = size - 1;
      documentsViewer.setOffset(off);
    });

    // 前のDoc見るボタン
    documentsViewer.prevButton.addActionListener(e -> {
      var offset = documentsViewer.offset;
      var size = documentsViewer.documents.size();
      var off = offset + 1;
      if (off >= size)
        off = 0;
      documentsViewer.setOffset(off);
    });


  }

}
