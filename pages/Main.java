import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Main {
    public static void main(String[] args) {
        // Create and set up the window.
        JFrame frame = new JFrame("シンクノート(仮)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add contents to the window.
        frame.setLayout(new GridLayout());

        var label = new JLabel("シンクノート(仮)へようこそ！");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);

        frame.add(label);

        frame.setSize(500, 300);
        // Display the window.
        frame.setVisible(true);

    }

    //東野がコメントを追加
}