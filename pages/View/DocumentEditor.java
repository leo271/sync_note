import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class DocumentEditor extends JPanel {

  public JTextArea textArea;
  public JButton deleteButton = new JButton("Delete");
  public JButton quitButton = new JButton("Quit");

  // コンストラクタ
  public DocumentEditor() {
    // パネルのレイアウト設定
    this.setLayout(new BorderLayout());
    createAndShowGUI();
  }

  // GUIの作成
  private void createAndShowGUI() {
    // JTextAreaの作成
    textArea = new JTextArea(10, 30); // 10行30列のテキストエリア
    textArea.setText("ここに入力してください");

    // JTextAreaをスクロールペインでラップ
    JScrollPane scrollPane = new JScrollPane(textArea);

    // JScrollPaneをパネルに追加
    this.add(scrollPane, BorderLayout.CENTER);

    // ボタン用のパネルを作成
    JPanel buttonPanel = new JPanel(new GridLayout(1, 2));

    // ボタンをパネルに追加
    buttonPanel.add(deleteButton);
    buttonPanel.add(quitButton);

    // ボタンパネルを下部に追加
    this.add(buttonPanel, BorderLayout.SOUTH);
  }

  // メインメソッド（テスト用）
  public static void main(String[] args) {
    // JFrameを作成してDocumentEditorを表示
    JFrame frame = new JFrame("Document Editor Test");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(800, 600);
    frame.add(new DocumentEditor());
    frame.setVisible(true);
  }
}
