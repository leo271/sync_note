package view;

import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

public class CellRenderer extends DefaultListCellRenderer {
  public enum CellType {
    HEAD, HEAD_GROUP, DOCUMENT, LIKED_DOCUMENT
  }

  // アイコンリソース
  private static final ImageIcon headGroupIcon =
      new ImageIcon(CellRenderer.class.getResource("../images/headgroup_icon.png"));
  private static final ImageIcon headIcon =
      new ImageIcon(CellRenderer.class.getResource("../images/head_icon.png"));
  private static final ImageIcon likedIcon =
      new ImageIcon(CellRenderer.class.getResource("../images/like.png"));
  private static final ImageIcon documentIcon =
      new ImageIcon(CellRenderer.class.getResource("../images/document.png"));

  private Function<String, CellType> type;

  public CellRenderer(Function<String, CellType> type) {
    this.type = type;
  }


  @Override
  public Component getListCellRendererComponent(JList<?> list, Object value, int index,
      boolean isSelected, boolean cellHasFocus) {
    JLabel label =
        (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

    switch (type.apply((String) value)) {
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

    return label;
  }
}
