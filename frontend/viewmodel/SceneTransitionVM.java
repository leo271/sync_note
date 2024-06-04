package viewmodel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.Header;
import view.SceneManager;

public class SceneTransitionVM {
  Header header;
  SceneManager sceneManager;


  public SceneTransitionVM(Header header, SceneManager sceneManager) {
    this.header = header;
    this.sceneManager = sceneManager;

    header.myListButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        sceneManager.switchPanel("myListView");
      }
    });

    header.searchHeadButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        sceneManager.switchPanel("searchView");
      }
    });

    header.searchHeadGroupButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        sceneManager.switchPanel("headGroupExplorer");
      }
    });

    header.headGroupButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        sceneManager.switchPanel("documentsViewer");
      }
    });

    header.documentEditorButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        sceneManager.switchPanel("documentEditor");
      }
    });
  }
}
