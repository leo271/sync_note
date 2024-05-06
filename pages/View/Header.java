import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Header extends JPanel {
    // private SceneManager sceneManager;

    public Header(/* SceneManager sceneManager */) {
        // this.sceneManager = sceneManager;

        JButton myListViewButton = new JButton("MyList");
        myListViewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // MyListViewに遷移
                // ここで何らかの処理を行う（例：MyListViewへの画面遷移処理）
                System.out.println("Transitioning to MyListView");
            }
        });

        JButton searchViewButton = new JButton("Search");
        searchViewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // SearchViewに遷移
                // ここで何らかの処理を行う（例：SearchViewへの画面遷移処理）
                System.out.println("Transitioning to SearchView");
            }
        });

        // ヘッダーにボタンを追加
        add(myListViewButton);
        add(searchViewButton);
    }
}
