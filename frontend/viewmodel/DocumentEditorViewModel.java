package viewmodel;

import view.DocumentEditor;
import javax.swing.text.BadLocationException;
import model.*;
import javax.swing.event.*;

public class DocumentEditorViewModel {

  public DocumentEditorViewModel(DocumentEditor documentEditor) {
    // ドキュメント保存
    documentEditor.previewArea.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void insertUpdate(DocumentEvent e) {
        updateLog(e);
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        updateLog(e);
      }

      private void updateLog(DocumentEvent e) {
        try {
          var content = e.getDocument().getText(0, e.getDocument().getLength());
          var newDocument = documentEditor.document.setContent(content);
          var res = DocumentController.updateContent(newDocument);
          if (res.hasError())
            return;
        } catch (BadLocationException badLocationException) {
          badLocationException.printStackTrace();
        }
      }

      @Override // 属性の変更
      public void changedUpdate(DocumentEvent e) {}
    });

    // ドキュメント削除
    documentEditor.deleteButton.addActionListener(e -> {
      DocumentController.delete(documentEditor.document.docID);
    });
  }
}
