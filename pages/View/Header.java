import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Header extends JPanel {
    // private SceneManager sceneManager;
    private JButton myListButton = new JButton("MyList");
    private JButton searchButton = new JButton("Search");

    public Header(/* SceneManager sceneManager */) {
        // this.sceneManager = sceneManager;

        this.setSize(500, 100);

        // 動作確認
        // myListButton.addActionListener(new ActionListener() {
        // @Override
        // public void actionPerformed(ActionEvent e) {
        // // MyListViewに遷移
        // // ここで何らかの処理を行う（例：MyListViewへの画面遷移処理）

        // System.out.println("Transitioning to MyListView");
        // }
        // });

        // searchButton.addActionListener(new ActionListener() {
        // @Override
        // public void actionPerformed(ActionEvent e) {
        // // SearchViewに遷移
        // // ここで何らかの処理を行う（例：SearchViewへの画面遷移処理）
        // System.out.println("Transitioning to SearchView");
        // }
        // });

        // ヘッダーにボタンを追加
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
