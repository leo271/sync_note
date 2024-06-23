package view;

import javax.swing.*;
import java.awt.*;

public class SearchView extends JPanel {

  public JTextField searchField;
  public JButton searchButton;

  public SearchView() {
    setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();

    JPanel contentPanel = new JPanel();
    contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

    JLabel label = new JLabel("検索したいHead/HeadGropuを入力してください");
    label.setAlignmentX(Component.CENTER_ALIGNMENT);
    contentPanel.add(label);

    contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

    JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    searchField = new JTextField(20);
    searchButton = new JButton("検索");

    searchPanel.add(searchField);
    searchPanel.add(searchButton);

    contentPanel.add(searchPanel);

    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 1.0;
    gbc.weighty = 1.0;
    gbc.anchor = GridBagConstraints.CENTER;
    add(contentPanel, gbc);

    // 推奨サイズを設定
    setPreferredSize(new Dimension(800, 600));
  }

}
