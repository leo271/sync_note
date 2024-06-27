package viewmodel;

import model.DocumentController;
import view.Scenes;

public class DocumentsViewerVM {
  public DocumentsViewerVM() {
    // ライク機能
    Scenes.documentsViewer.likeButton.addActionListener(e -> {
      var document = Scenes.documentsViewer.activeDocument;
      DocumentController.toggleLike(document);
      document.like++;
      var documents = DocumentController.getFromHead(document.head);
      if (documents.hasError()) {
        System.out.println("Error: " + documents.message);
        return;
      }
      Scenes.documentsViewer.setDocuments(document.head, documents.message, document.docID); // その場でローカル上のライクを増やす
    });

    // 次のDoc見るボタン
    Scenes.documentsViewer.nextButton.addActionListener(e -> {
      var offset = Scenes.documentsViewer.offset;
      var size = Scenes.documentsViewer.documents.size();
      var off = offset + 1;
      if (off >= size)
        off = 0;
      Scenes.documentsViewer.setOffset(off);
    });

    // 前のDoc見るボタン
    Scenes.documentsViewer.prevButton.addActionListener(e -> {
      var offset = Scenes.documentsViewer.offset;
      var size = Scenes.documentsViewer.documents.size();
      var off = offset - 1;
      if (off < 0)
        off = size - 1;
      Scenes.documentsViewer.setOffset(off);
    });
  }
}
