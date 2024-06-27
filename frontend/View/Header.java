package view;

import javax.swing.*;
import java.awt.*;
import utility.Colors;

public class Header extends JPanel {
  public JButton myListButton = new JButton("マイリスト");
  public JButton searchButton = new JButton("検索");
  public JButton explorerButton = new JButton("エクスプローラー");

  public Header() {
    // Set background color using Colors class
    this.setBackground(Colors.darkRed);

    // Set the layout manager to GridLayout for equal spacing
    this.setLayout(new GridLayout(1, 3, 10, 0));

    // Customize buttons using Colors class
    customizeButton(myListButton);
    customizeButton(searchButton);
    customizeButton(explorerButton);

    // Add buttons to the panel
    add(myListButton);
    add(searchButton);
    add(explorerButton);

    // Set panel preferred size
    this.setPreferredSize(new Dimension(400, 50));
  }

  private void customizeButton(JButton button) {
    // Set button colors and font
    button.setForeground(Color.WHITE);
    button.setFont(new Font("Arial", Font.PLAIN, 14));

    // Set button border
    button.setBorder(BorderFactory.createLineBorder(Colors.darkRed, 1));

    // Set button size
    button.setPreferredSize(new Dimension(120, 30));
    button.setFocusPainted(false);
  }

  public void setSelectedButton(JButton selectedButton) {
    resetButtonStyles();
    selectedButton.setBackground(Colors.lightRed);
    selectedButton.setForeground(Color.WHITE);
    // Increase the thickness of the underline and move it up slightly
    selectedButton.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Colors.blurRed));
  }

  public void resetButtonStyles() {
    myListButton.setForeground(Color.WHITE);
    myListButton.setBorder(BorderFactory.createLineBorder(Colors.darkRed, 1));

    searchButton.setForeground(Color.WHITE);
    searchButton.setBorder(BorderFactory.createLineBorder(Colors.darkRed, 1));

    explorerButton.setForeground(Color.WHITE);
    explorerButton.setBorder(BorderFactory.createLineBorder(Colors.darkRed, 1));
  }
}
