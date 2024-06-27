// ここにテストを記述していく

import model.Document;
import model.DocumentController;
import model.HeadsController;
import model.LocalDatabaseInterface;
import model.db_enum.DB;

public class Test {
  public static void main(String[] args) {
    testModel();
  }

  private static void testModel() {
    testRemote();
  }

  private static void testRemote() {
    measure(() -> {
      var res = HeadsController.createHead("root", "G");
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
      var myDocuments = DocumentController.getLikedDocuments();
      if (myDocuments.hasError()) {
        System.out.println("Error: " + myDocuments.error);
        return;
      }
      for (var document : myDocuments.message) {
        System.out.println(document.toJSON(true).toString());
      }
    });
    measure(() -> {
      var response = DocumentController.createDocument("Mathematics");
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
      var doc =
          new Document(response.message, "Mathematics", "Mathematics is the study of numbers", 0);
      doc.like();
      var res = DocumentController.updateDocument(doc);
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
      res = DocumentController.deleteDocument("be837244-6835-4102-9ae7-7785ec1fe1ee");
      if (res.hasError()) {
        System.out.println("Error: " + res.error);
        return;
      }
      res = DocumentController.deleteDocument("leohagi3");
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
