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
    var res = DocumentController.createFromOther("メロンパンの情報求ム");
    if (res.hasError()) {
      System.out.println("Error: " + res.error);
      return;
    }
    var doc = new Document(res.message.docID, res.message.head, "# LLama3\nMelon pan! 🍞️💕\n" + //
        "Melon pan is a popular Japanese sweet bread that is loved by many. Here are some key facts about it:\n"
        + //
        "**What is Melon Pan?**\n" + //
        "Melon pan is a type of Japanese bread that is characterized by its crispy cookie-like topping and soft, fluffy interior. The name \"melon pan\" literally translates to \"melon bread,\" but it doesn't actually contain any melon.\n"
        + //
        "**History**\n" + //
        "Melon pan originated in Japan in the 1960s and quickly gained popularity. It is believed to have been created by a Japanese baker who was inspired by the French baguette.\n"
        + //
        "**Ingredients**\n" + //
        "The dough typically consists of flour, yeast, sugar, eggs, and milk. The cookie-like topping is made from a mixture of flour, sugar, and butter.\n"
        + //
        "**Taste and Texture**\n" + //
        "Melon pan has a crispy, crunchy exterior and a soft, fluffy interior. The cookie topping adds a sweet and slightly crunchy texture to the bread.\n"
        + //
        "**Variations**\n" + //
        "There are many variations of melon pan, including flavors such as matcha, chocolate, and strawberry. Some bakeries also add nuts, seeds, or dried fruit to the dough for added texture and flavor.\n"
        + //
        "**How to Eat**\n" + //
        "Melon pan is often eaten as a snack or dessert, and it's commonly paired with a cup of coffee or tea. It's also a popular ingredient in Japanese-style sandwiches and toast.\n"
        + //
        "**Fun Facts**\n" + //
        "* Melon pan is a popular souvenir among tourists visiting Japan.\n" + //
        "* The crispy cookie topping is made by applying a special type of sugar to the bread before baking.\n"
        + //
        "* Melon pan is often served at Japanese cafes and bakeries, and it's a popular item on menus.\n"
        + //
        "I hope you enjoyed learning more about melon pan! 😊", 20);
    DocumentController.updateContent(doc, false);
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
      var res = DocumentController.updateContent(doc, false);
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
