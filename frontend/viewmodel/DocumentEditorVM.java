package viewmodel;

import view.DocumentEditor;
import view.SceneManager;
import javax.swing.text.BadLocationException;
import model.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.*;

public class DocumentEditorVM {

  public DocumentEditorVM(DocumentEditor documentEditor, SceneManager sceneManager) {
    // ドキュメント保存
    documentEditor.editArea.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void insertUpdate(DocumentEvent e) {
        updateLog(e);
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        updateLog(e);
      }

      private void updateLog(DocumentEvent e) {
        if (documentEditor.document == null || documentEditor.document.head.isEmpty())
          return;
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
      if (documentEditor.document == null || documentEditor.document.head.isEmpty())
        return;
      DocumentController.delete(documentEditor.document.docID);
      sceneManager.showPanel(SceneManager.Panel.MyListView);
    });

    // アクション
    documentEditor.editButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        documentEditor.setEdit(true);
      }
    });
    documentEditor.previewButton.setForeground(Color.BLACK);
    documentEditor.editButton.setForeground(Color.BLUE);
    documentEditor.previewButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        documentEditor.setEdit(false);
      }
    });
  }
}
