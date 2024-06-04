package backend;

import java.util.HashMap;
import backend.model.*;

public class Main {
  public static void main(String[] args) {
    System.out.println("Hello, World!");
    try {
      var namespaceRow = NameSpaceCols.intoDB(new HashMap<NameSpaceCols, String>() {
        {
          put(NameSpaceCols.NAME, "name");
          put(NameSpaceCols.Type, "H");
        }
      });
      System.out.println(namespaceRow.str);
      DatabaseController.getInstance().insert(Table.NAME_SPACE, namespaceRow);
      var headGroupRow = HeadGroupCols.intoDB(new HashMap<HeadGroupCols, String>() {
        {
          put(HeadGroupCols.NAME, "name");
          put(HeadGroupCols.CHILD, "child");
          put(HeadGroupCols.Type, "H");
        }
      });
      System.out.println(headGroupRow.str);
      DatabaseController.getInstance().insert(Table.HEAD_GROUP, headGroupRow);
      System.out.println("Inserted");
      var result = DatabaseController.getInstance().searchSingle(Table.NAME_SPACE,
          NameSpaceCols.NAME, "name");
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
