package viewmodel;

import view.DocumentEditor;
import model.*;

public class DocumentEditorViewModel {

  public DocumentEditorViewModel(DocumentEditor documentEditor) {

    var document = documentEditor.document;

    // ドキュメント保存
    documentEditor.saveButton.addActionListener(e -> {
      document.content = documentEditor.previewArea.getText(); // テキストエリアの内容を反映
      DocumentController.updateDocument(document);
    });

    // ドキュメント削除
    documentEditor.deleteButton.addActionListener(e -> {
      DocumentController.deleteDocument(document.docID);
    });



  }

}
