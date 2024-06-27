package utility;

import java.awt.*;
import javax.swing.*;

public class Dialog {
    public static void show(Component parent, String title, String message) {
        // Create a custom dialog
        JDialog dialog = new JDialog((Frame) null, title, true);
        dialog.setLayout(new BorderLayout());

        // Add a message using JLabel with HTML for line wrapping
        JLabel messageLabel =
                new JLabel("<html><body style='width: 300px;'>" + message + "</body></html>");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        messageLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        dialog.add(messageLabel, BorderLayout.CENTER);

        // Create an OK button with custom color
        JButton okButton = new JButton("OK");
        okButton.setBackground(Colors.darkRed);
        okButton.setForeground(Color.WHITE);
        okButton.setFont(new Font("Arial", Font.PLAIN, 14));
        okButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Colors.darkRed, 1, true),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        okButton.setFocusPainted(false);
        okButton.setOpaque(true);

        okButton.addActionListener(e -> dialog.dispose());

        // Add the button to the dialog
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(parent.getBackground()); // Set background color to match parent
        buttonPanel.add(okButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        // Set the dialog size and make it visible
        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
    }

    public static String input(Component parent, String title, String message) {
        // Create a custom dialog
        JDialog dialog = new JDialog((Frame) null, title, true);
        dialog.setLayout(new BorderLayout());

        // Add a message using JLabel with HTML for line wrapping
        JLabel messageLabel =
                new JLabel("<html><body style='width: 300px;'>" + message + "</body></html>");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        messageLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        dialog.add(messageLabel, BorderLayout.NORTH);

        // Create an input field with smaller height
        JTextField inputField = new JTextField();
        inputField.setFont(new Font("Arial", Font.PLAIN, 14));
        inputField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1, true),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        // Wrap the input field in a panel to control its size
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(inputField, BorderLayout.NORTH);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        dialog.add(inputPanel, BorderLayout.CENTER);

        // Create OK and Cancel buttons with custom colors
        JButton okButton = new JButton("OK");
        okButton.setBackground(Colors.darkRed);
        okButton.setForeground(Color.WHITE);
        okButton.setFont(new Font("Arial", Font.PLAIN, 14));
        okButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Colors.darkRed, 1, true),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        okButton.setFocusPainted(false);
        okButton.setOpaque(true);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBackground(Color.GRAY);
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFont(new Font("Arial", Font.PLAIN, 14));
        cancelButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1, true),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        cancelButton.setFocusPainted(false);
        cancelButton.setOpaque(true);

        final String[] userInput = {null}; // Array to hold the user input

        okButton.addActionListener(e -> {
            userInput[0] = inputField.getText();
            dialog.dispose();
        });

        cancelButton.addActionListener(e -> {
            dialog.dispose();
        });

        // Add the buttons to the dialog
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(parent.getBackground()); // Set background color to match parent
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        // Set the dialog size and make it visible
        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);

        return userInput[0];
    }

}
