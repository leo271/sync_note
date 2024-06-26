package view;

import javax.swing.*;

public class Header extends JPanel {
  // public SceneManager sceneManager;
  public JButton myListButton = new JButton("My List");
  public JButton searchButton = new JButton("Search Head");
  public JButton explorerButton = new JButton("Head Group Explorer");
  public JButton editorButton = new JButton("Edit Document");
  public JButton viewerButton = new JButton("Documents Viewer");


  public Header() {
    this.setSize(getPreferredSize());
    // ヘッダーにボタンを追加
    add(myListButton);
    add(searchButton);
    add(explorerButton);
    add(editorButton);
    add(viewerButton);
  }

}
