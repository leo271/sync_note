// ここにテストを記述していく

import model.Document;
import model.DocumentController;
import model.HeadsController;
import model.LocalDatabaseInterface;
import model.db_enum.DB;

public class Test {
  public static void main(String[] args) {
    createDemoDocuments();
  }

  private static void createDemoDocuments() {
    var res = DocumentController.create("３春オペレーティングシステム");
    if (res.hasError()) {
      System.out.println("Error: " + res.error);
      return;
    }
    var doc = new Document(res.message.docID, res.message.head, "<!DOCTYPE html>\n" + //
        "<html lang=\"ja\">\n" + //
        "<head>\n" + //
        "    <meta charset=\"UTF-8\">\n" + //
        "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" + //
        "    <title>オペレーティングシステムについて</title>\n" + //
        "</head>\n" + //
        "<body>\n" + //
        "    <h1>オペレーティングシステムについて</h1>\n" + //
        "    <p>オペレーティングシステム（Operating System, OS）は、コンピュータのハードウェアとソフトウェアの間で仲介役を果たすソフトウェアです。OSは、ユーザーがコンピュータを効率的かつ簡単に使用できるようにし、プログラムの実行やハードウェアリソースの管理を行います。</p>\n"
        + //
        "\n" + //
        "    <h2>主な機能</h2>\n" + //
        "    <ul>\n" + //
        "        <li><strong>プロセス管理:</strong> OSは、プログラムの実行を管理し、複数のプログラムが同時に実行される場合にリソースを適切に配分します。</li>\n"
        + //
        "        <li><strong>メモリ管理:</strong> OSは、プログラムが利用するメモリを管理し、効率的なメモリの使用を確保します。</li>\n" + //
        "        <li><strong>ファイルシステム管理:</strong> OSは、データの保存とアクセスを管理し、ファイルとディレクトリの構造を提供します。</li>\n"
        + //
        "        <li><strong>入出力管理:</strong> OSは、キーボード、マウス、ディスクドライブ、ネットワークインターフェースなどのハードウェアデバイスとのやり取りを管理します。</li>\n"
        + //
        "        <li><strong>ユーザーインターフェース:</strong> OSは、ユーザーがコンピュータとやり取りするためのインターフェースを提供します。これには、グラフィカルユーザーインターフェース（GUI）やコマンドラインインターフェース（CLI）が含まれます。</li>\n"
        + //
        "    </ul>\n" + //
        "\n" + //
        "    <h2>代表的なオペレーティングシステム</h2>\n" + //
        "    <ul>\n" + //
        "        <li><strong>Windows:</strong> マイクロソフト社が開発したOSで、デスクトップおよびラップトップコンピュータで広く使用されています。</li>\n"
        + //
        "        <li><strong>macOS:</strong> Apple社が開発したOSで、Macコンピュータに搭載されています。使いやすさとデザインが特徴です。</li>\n"
        + //
        "        <li><strong>Linux:</strong> オープンソースのOSで、様々なディストリビューションがあります。サーバーやエンタープライズ環境でよく使われます。</li>\n"
        + //
        "        <li><strong>Android:</strong> Googleが開発したモバイルOSで、スマートフォンやタブレットに広く普及しています。</li>\n" + //
        "        <li><strong>iOS:</strong> Apple社のモバイルOSで、iPhoneやiPadに搭載されています。</li>\n" + //
        "    </ul>\n" + //
        "\n" + //
        "    <h2>まとめ</h2>\n" + //
        "    <p>オペレーティングシステムは、コンピュータの効率的な動作とユーザーの利便性を支える重要なソフトウェアです。異なるOSは、それぞれ異なる特性と利点を持ち、様々な用途に応じて選ばれます。</p>\n"
        + //
        "</body>\n" + //
        "</html>\n" + //
        "", 30);
    DocumentController.updateContent(doc);
    res = DocumentController.create("３春オペレーティングシステム");
    if (res.hasError()) {
      System.out.println("Error: " + res.error);
      return;
    }
    doc = new Document(res.message.docID, res.message.head,
        "オペレーティングシステムは、コンピュータの基本ソフトウェアの一つであり、ハードウェアとソフトウェアの間に立って、ユーザーがコンピュータを利用するためのインタフェースを提供する。",
        20);
    DocumentController.updateContent(doc);
    res = DocumentController.create("３春オペレーティングシステム");
    if (res.hasError()) {
      System.out.println("Error: " + res.error);
      return;
    }
    doc = new Document(res.message.docID, res.message.head, "コンピュータの基本ソフトウェアの一つである。", 3);
    DocumentController.updateContent(doc);
  }

  @SuppressWarnings("unused")
  private static void testModel() {
    testRemote();
  }

  private static void testRemote() {
    measure(() -> {
      var res = HeadsController.create("root", "G");
      if (res.hasError()) {
        System.out.println("Error: " + res.error);
        return;
      } else {
        System.out.println("Success: " + res.message);
      }
    });
  }

  @SuppressWarnings("unused")
  private static void testDocument() {
    measure(() -> {
      var myDocuments = DocumentController.getMyDocuments();
      System.out.println("Previous");
      if (myDocuments.hasError()) {
        System.out.println("Error: " + myDocuments.error);
        return;
      }
      for (var document : myDocuments.message) {
        System.out.println(document.toJSON(true).toString());
      }
    });
    measure(() -> {
      var local = LocalDatabaseInterface.getInstance();
      var sampleDocument = new Document("leohagi4",
          "This is a sample documentsssss." + System.currentTimeMillis(), "Sample", 0);
      var res = local.upsert(DB.DOCUMENT, sampleDocument.toJSON(true));
      System.out.println("After");
      if (res != 200) {
        System.out.println("Error: " + res);
        return;
      }
      var myDocuments = DocumentController.getMyDocuments();
      if (myDocuments.hasError()) {
        System.out.println("Error: " + myDocuments.error);
        return;
      }
      for (var document : myDocuments.message) {
        System.out.println(document.toJSON(true).toString());
      }
    });
    measure(() -> {
      var myDocuments = DocumentController.getLiked();
      if (myDocuments.hasError()) {
        System.out.println("Error: " + myDocuments.error);
        return;
      }
      for (var document : myDocuments.message) {
        System.out.println(document.toJSON(true).toString());
      }
    });
    measure(() -> {
      var response = DocumentController.create("Mathematics");
      if (response.hasError()) {
        System.out.println("Error: " + response.error);
        return;
      }
      var myDocuments = DocumentController.getMyDocuments();
      if (myDocuments.hasError()) {
        System.out.println("Error: " + myDocuments.error);
        return;
      }
      for (var document : myDocuments.message) {
        System.out.println(document.toJSON(true).toString());
      }
      var doc = new Document(response.message.docID, "Mathematics",
          "Mathematics is the study of numbers", 0);
      doc.like();
      var res = DocumentController.updateContent(doc);
      if (res.hasError()) {
        System.out.println("Error: " + res.error);
        return;
      }
      myDocuments = DocumentController.getMyDocuments();
      if (myDocuments.hasError()) {
        System.out.println("Error: " + myDocuments.error);
        return;
      }
      for (var document : myDocuments.message) {
        System.out.println(document.toJSON(true).toString());
      }
      res = DocumentController.delete("be837244-6835-4102-9ae7-7785ec1fe1ee");
      if (res.hasError()) {
        System.out.println("Error: " + res.error);
        return;
      }
      res = DocumentController.delete("leohagi3");
      if (res.hasError()) {
        System.out.println("Error: " + res.error);
        return;
      }
      myDocuments = DocumentController.getMyDocuments();
      if (myDocuments.hasError()) {
        System.out.println("Error: " + myDocuments.error);
        return;
      }
      for (var document : myDocuments.message) {
        System.out.println(document.toJSON(true).toString());
      }
    });
  }

  private static void measure(Runnable task) {
    long startTime = System.currentTimeMillis();

    // タスクの実行
    task.run();

    long endTime = System.currentTimeMillis();
    long executionTime = endTime - startTime;

    System.out.println("Execution time: " + executionTime + " milliseconds");
  }
}
