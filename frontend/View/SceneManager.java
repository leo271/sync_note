package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SceneManager extends JFrame {
  public CardLayout cardLayout;
  public JPanel cardPanel;

  public SceneManager(Header header, MyListView myListView, SearchView searchView,
      HeadGroupExplorer headGroupExplorer, DocumentsViewer documentsViewer,
      DocumentEditor documentEditor) {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(getPreferredSize());
    var mainPanel = new JPanel(new BorderLayout());
    mainPanel.add(header, BorderLayout.NORTH);
    this.cardLayout = new CardLayout();
    this.cardPanel = new JPanel(cardLayout);
    cardPanel.add(myListView, "myListView");
    cardPanel.add(searchView, "searchView");
    cardPanel.add(headGroupExplorer, "headGroupExplorer");
    cardPanel.add(documentsViewer, "documentsViewer");
    cardPanel.add(documentEditor, "documentEditor");
    mainPanel.add(cardPanel, BorderLayout.CENTER);
    cardLayout.show(cardPanel, "myListView"); // デフォルトでMyListViewを表示

    this.add(mainPanel);
  }

  public void switchPanel(String panelName) {
    cardLayout.show(cardPanel, panelName);
  }
}
