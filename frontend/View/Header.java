import javax.swing.*;

public class Header extends JPanel {
  // public SceneManager sceneManager;
  public JButton myListButton = new JButton("MyList");
  public JButton searchButton = new JButton("Search");

  public Header(/* SceneManager sceneManager */) {
    // this.sceneManager = sceneManager;

    this.setSize(getPreferredSize());

    // ヘッダーにボタンを追加
    add(myListButton);
    add(searchButton);
  }

}
