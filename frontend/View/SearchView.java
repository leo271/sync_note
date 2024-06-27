package view;

import javax.swing.*;
import java.awt.*;
import utility.Colors;

public class SearchView extends JPanel {

  public JTextField searchField;
  public JButton searchButton;

  public SearchView() {
    setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();

    JPanel contentPanel = new JPanel();
    contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
    contentPanel.setBackground(Colors.lightGray); // Set background color

    JLabel label = new JLabel("検索したいHead/HeadGroupを入力してください");
    label.setAlignmentX(Component.CENTER_ALIGNMENT);
    label.setFont(new Font("Arial", Font.BOLD, 16)); // Customize font
    contentPanel.add(label);

    contentPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Add spacing

    JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    searchPanel.setBackground(Colors.lightGray); // Set background color
    searchField = new JTextField(20);
    searchButton = new JButton("検索");

    customizeTextField(searchField); // Customize the text field
    customizeButton(searchButton); // Customize the button

    searchPanel.add(searchField);
    searchPanel.add(searchButton);

    contentPanel.add(searchPanel);

    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 1.0;
    gbc.weighty = 1.0;
    gbc.anchor = GridBagConstraints.CENTER;
    add(contentPanel, gbc);

    // Set preferred size
    setPreferredSize(new Dimension(800, 600));
  }

  // Customize the text field
  private void customizeTextField(JTextField textField) {
    textField.setFont(new Font("Arial", Font.PLAIN, 14));
    textField.setBorder(
        BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Colors.darkRed, 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)));
    textField.setBackground(Color.WHITE);
    textField.setForeground(Color.BLACK);
  }

  // Customize the button
  private void customizeButton(JButton button) {
    button.setBackground(Colors.darkRed);
    button.setForeground(Color.WHITE);
    button.setFont(new Font("Arial", Font.PLAIN, 14));
    button.setBorder(
        BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Colors.darkRed, 1, true),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)));
    button.setFocusPainted(false);
    button.setOpaque(true);
  }
}
