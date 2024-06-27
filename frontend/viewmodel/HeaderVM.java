package viewmodel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.Header;
import view.MyListView;
import view.SceneManager;

public class HeaderVM {
  Header header;
  SceneManager sceneManager;

  public HeaderVM(Header header, MyListView myListView, SceneManager sceneManager) {
    this.header = header;
    this.sceneManager = sceneManager;
    sceneManager.showPanel(SceneManager.Panel.MyListView);
    header.setSelectedButton(header.myListButton);
    header.myListButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        myListView.refreshButton.doClick();
        sceneManager.showPanel(SceneManager.Panel.MyListView);
        header.setSelectedButton(header.myListButton);
      }
    });

    header.searchButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        sceneManager.showPanel(SceneManager.Panel.SearchView);
        header.setSelectedButton(header.searchButton);
      }
    });

    header.explorerButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        sceneManager.showPanel(SceneManager.Panel.HeadGroupExplorer);
        header.setSelectedButton(header.explorerButton);
      }
    });
  }
}
