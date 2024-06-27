package viewmodel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.SceneManager;
import view.Scenes;

public class HeaderVM {
  public HeaderVM() {
    Scenes.sceneManager.showPanel(SceneManager.Panel.MyListView);
    Scenes.header.setSelectedButton(Scenes.header.myListButton);
    Scenes.header.myListButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Scenes.myListView.refreshButton.doClick();
        Scenes.sceneManager.showPanel(SceneManager.Panel.MyListView);
        Scenes.header.setSelectedButton(Scenes.header.myListButton);
      }
    });

    Scenes.header.searchButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Scenes.sceneManager.showPanel(SceneManager.Panel.SearchView);
        Scenes.header.setSelectedButton(Scenes.header.searchButton);
      }
    });

    Scenes.header.explorerButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Scenes.sceneManager.showPanel(SceneManager.Panel.HeadGroupExplorer);
        Scenes.header.setSelectedButton(Scenes.header.explorerButton);
      }
    });
  }
}
