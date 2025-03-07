package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SceneManager extends JFrame {
  public enum Panel {
    MyListView, SearchView, HeadGroupExplorer, DocumentsViewer, DocumentEditor,
  }

  public CardLayout cardLayout;
  public JPanel cardPanel;
  public Panel current;
  private Header header;

  public SceneManager(Header header, MyListView myListView, SearchView searchView,
      HeadGroupExplorer headGroupExplorer, DocumentsViewer documentsViewer,
      DocumentEditor documentEditor) {
    this.header = header;
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(getPreferredSize());
    var mainPanel = new JPanel(new BorderLayout());
    mainPanel.add(header, BorderLayout.NORTH);
    this.cardLayout = new CardLayout();
    this.cardPanel = new JPanel(cardLayout);
    cardPanel.add(myListView, Panel.MyListView.name());
    cardPanel.add(searchView, Panel.SearchView.name());
    cardPanel.add(headGroupExplorer, Panel.HeadGroupExplorer.name());
    cardPanel.add(documentsViewer, Panel.DocumentsViewer.name());
    cardPanel.add(documentEditor, Panel.DocumentEditor.name());
    showPanel(Panel.MyListView);
    mainPanel.add(cardPanel, BorderLayout.CENTER);

    this.add(mainPanel);
  }

  public void showPanel(Panel panel) {
    current = panel;
    cardLayout.show(cardPanel, panel.name());
    header.explorerButton.setForeground(Color.BLACK);
    header.myListButton.setForeground(Color.BLACK);
    header.searchButton.setForeground(Color.BLACK);
    switch (panel) {
      case MyListView:
        header.myListButton.setForeground(Color.BLUE);
        break;
      case SearchView:
        header.searchButton.setForeground(Color.BLUE);
        break;
      case HeadGroupExplorer:
        header.explorerButton.setForeground(Color.BLUE);
        break;
      default:
        break;
    }
  }

  public boolean is(Panel panel) {
    return current == panel;
  }
}
