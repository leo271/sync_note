package viewmodel;

import view.SceneManager;
import view.Scenes;
import javax.swing.text.BadLocationException;
import model.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.*;

public class DocumentEditorVM {
  public DocumentEditorVM() {
    // ドキュメント保存
    Scenes.documentEditor.editArea.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void insertUpdate(DocumentEvent e) {
        updateLog(e);
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        updateLog(e);
      }

      private void updateLog(DocumentEvent e) {
        if (Scenes.documentEditor.document == null || Scenes.documentEditor.document.head.isEmpty())
          return;
        try {
          var content = e.getDocument().getText(0, e.getDocument().getLength());
          var newDocument = Scenes.documentEditor.document.setContent(content);
          var res = DocumentController.updateContent(newDocument, true);
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
    Scenes.documentEditor.deleteButton.addActionListener(e -> {
      if (Scenes.documentEditor.document == null || Scenes.documentEditor.document.head.isEmpty())
        return;
      DocumentController.delete(Scenes.documentEditor.document.docID);
      Scenes.sceneManager.showPanel(SceneManager.Panel.MyListView);
    });

    // アクション
    Scenes.documentEditor.editButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Scenes.documentEditor.setEdit(true);
      }
    });
    Scenes.documentEditor.previewButton.setForeground(Color.BLACK);
    Scenes.documentEditor.editButton.setForeground(Color.BLUE);
    Scenes.documentEditor.previewButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Scenes.documentEditor.setEdit(false);
      }
    });
  }
}
