package viewmodel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.Header;
import view.SceneManager;

public class HeaderVM {
  Header header;
  SceneManager sceneManager;


  public HeaderVM(Header header, SceneManager sceneManager) {
    this.header = header;
    this.sceneManager = sceneManager;

    header.myListButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        sceneManager.showPanel(SceneManager.Panel.MyListView);
      }
    });

    header.searchButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        sceneManager.showPanel(SceneManager.Panel.SearchView);
      }
    });

    header.explorerButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        sceneManager.showPanel(SceneManager.Panel.HeadGroupExplorer);
      }
    });

    header.viewerButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        sceneManager.showPanel(SceneManager.Panel.DocumentsViewer);
      }
    });

    header.editorButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        sceneManager.showPanel(SceneManager.Panel.DocumentEditor);
      }
    });
  }
}
