import javax.swing.*;

public class Header extends JPanel {
    // public SceneManager sceneManager;
    public JButton myListButton = new JButton("MyList");
    public JButton searchButton = new JButton("Search");

    public Header(/* SceneManager sceneManager */) {
        // this.sceneManager = sceneManager;

        this.setSize(getPreferredSize());


        add(myListButton);
        add(searchButton);
    }

    public JButton getMyListButton() {
        return myListButton;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

}
