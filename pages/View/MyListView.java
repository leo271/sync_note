import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MyListView extends JPanel {
    private List<Head> myList;
    private SceneManager sceneManager;

    public MyListView(List<Head> myList, SceneManager sceneManager) {
        this.myList = myList;
        this.sceneManager = sceneManager;

        setLayout(new BorderLayout());

        // マイリストをリスト形式で表示
        JList<Head> list = new JList<>(myList.toArray(new Head[0]));
        add(new JScrollPane(list), BorderLayout.CENTER);

        // ヘッダーにドキュメント追加ボタンを追加
        JButton addDocumentButton = new JButton("Add Document");
        addDocumentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // ドキュメント追加の処理
                // ここではDocumentEditorへの画面遷移処理がないため、その旨を出力
                System.out.println("Transitioning to DocumentEditor");
            }
        });
        add(addDocumentButton, BorderLayout.NORTH);

        // リストの要素からDocumentViewerへ画面遷移する処理
        list.addListSelectionListener(e -> {
            Head selectedHead = list.getSelectedValue();
            if (selectedHead != null) {
                sceneManager.goToDocumentViewer(selectedHead);
            }
        });

        // リストの要素からHeadGroupExplorerへ画面遷移するボタンを追加
        JButton exploreButton = new JButton("Explore");
        exploreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // HeadGroupExplorerへの画面遷移処理がないため、その旨を出力
                System.out.println("Transitioning to HeadGroupExplorer");
            }
        });
        add(exploreButton, BorderLayout.SOUTH);
    }
}
