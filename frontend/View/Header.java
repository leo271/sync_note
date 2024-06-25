package view;

import javax.swing.*;

public class Header extends JPanel {
  // public SceneManager sceneManager;
  public JButton myListButton = new JButton("My List");
  public JButton searchHeadButton = new JButton("Search Head");
  public JButton searchHeadGroupButton = new JButton("Search Head Group");
  public JButton documentEditorButton = new JButton("Edit Document");
  public JButton headGroupButton = new JButton("Documents Viewer");


  public Header(/* SceneManager sceneManager */) {
    // this.sceneManager = sceneManager;

    this.setSize(getPreferredSize());
    // ヘッダーにボタンを追加
    add(myListButton);
    add(searchHeadButton);
    add(searchHeadGroupButton);
    add(documentEditorButton);
    add(headGroupButton);
  }

}
