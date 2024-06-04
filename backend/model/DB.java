package backend.model;

import java.util.HashSet;

public class DB {
  public static Column DOC_ID = new Column("DocId", ColumnType.TXT36);
  public static Column CONTENT = new Column("Content", ColumnType.TXT);
  public static Column HEAD_NAME = new Column("HeadName", ColumnType.TXT100);
  public static Column GROUP_NAME = new Column("GroupName", ColumnType.TXT100);
  public static Column NAME = new Column("Name", ColumnType.TXT100);
  public static Column LIKE = new Column("Like", ColumnType.INT);
  public static Column TYPE_HG = new Column("TypeHG", ColumnType.TYPE_HG);
  public static Column TYPE_ML = new Column("TypeML", ColumnType.TYPE_ML);

  public static Table DOCUMENT = new Table("Document", new HashSet<Column>() {
    {
      add(DOC_ID);
      add(CONTENT);
      add(HEAD_NAME);
      add(LIKE);
    }
  });
  public static Table HEAD_GROUP = new Table("HeadGroup", new HashSet<Column>() {
    {
      add(GROUP_NAME);
      add(LIKE);
      add(NAME);
      add(TYPE_HG);
    }
  });
  public static Table NAME_SPACE = new Table("NameSpace", new HashSet<Column>() {
    {
      add(NAME);
      add(TYPE_HG);
    }
  });

  public static boolean isValidValue(Column column, String str) {
    switch (column.type) {
      case ColumnType.INT:
        try {
          Integer.parseInt(str);
          return true;
        } catch (NumberFormatException e) {
          return false;
        }
      case ColumnType.TXT:
        return true;
      case ColumnType.TXT100:
        return str.length() <= 100;
      case ColumnType.TXT36:
        return str.length() <= 36;
      case ColumnType.TYPE_HG:
        return str.equals("H") || str.equals("G");
      case ColumnType.TYPE_ML:
        return str.equals("M") || str.equals("L");
      default:
        return false;
    }
  }
}
