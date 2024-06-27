package view;

import javax.swing.*;
import java.awt.*;
import java.util.function.Function;
import utility.Colors;

public class CellRenderer extends DefaultListCellRenderer {
  public enum CellType {
    HEAD, HEAD_GROUP, DOCUMENT, LIKED_DOCUMENT
  }

  // Icon resources with fixed size
  private static final ImageIcon headGroupIcon = scaleIcon(
      new ImageIcon(CellRenderer.class.getResource("../images/headgroup_icon.png")), 32, 32);
  private static final ImageIcon headIcon =
      scaleIcon(new ImageIcon(CellRenderer.class.getResource("../images/head_icon.png")), 32, 32);
  private static final ImageIcon likedIcon =
      scaleIcon(new ImageIcon(CellRenderer.class.getResource("../images/like.png")), 32, 32);
  private static final ImageIcon documentIcon =
      scaleIcon(new ImageIcon(CellRenderer.class.getResource("../images/document.png")), 32, 32);

  private Function<String, CellType> type;

  public CellRenderer(Function<String, CellType> type) {
    this.type = type;
  }

  @Override
  public Component getListCellRendererComponent(JList<?> list, Object value, int index,
      boolean isSelected, boolean cellHasFocus) {
    JLabel label =
        (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

    String text = (String) value;
    label.setText(text);

    switch (type.apply(text)) {
      case HEAD_GROUP:
        label.setIcon(headGroupIcon);
        label.setFont(label.getFont().deriveFont(Font.BOLD));
        break;
      case HEAD:
        label.setIcon(headIcon);
        label.setFont(label.getFont().deriveFont(Font.PLAIN));
        break;
      case DOCUMENT:
        label.setIcon(documentIcon);
        label.setFont(label.getFont().deriveFont(Font.PLAIN));
        break;
      case LIKED_DOCUMENT:
        label.setIcon(likedIcon);
        label.setFont(label.getFont().deriveFont(Font.PLAIN));
        break;
    }

    // Set the background color for selected and non-selected states
    if (isSelected) {
      label.setBackground(Colors.lightRed);
      label.setForeground(Color.WHITE);
    } else {
      label.setBackground(Colors.lightGray);
      label.setForeground(Color.BLACK);
    }

    // Add a bottom border for each cell for better separation
    label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Colors.grey));

    // Add padding to the icon
    label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5)); // Top, left, bottom, right
                                                                   // padding
    label.setIconTextGap(10); // 10 pixels gap between icon and text

    return label;
  }

  private static ImageIcon scaleIcon(ImageIcon icon, int width, int height) {
    Image img = icon.getImage();
    Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    return new ImageIcon(scaledImg);
  }
}
