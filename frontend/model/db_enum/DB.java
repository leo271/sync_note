package model.db_enum;

import java.util.HashSet;
import model.JSON;

public class DB {
  public static Column DOC_ID = new Column("DocId", ColumnType.TXT36);
  public static Column CONTENT = new Column("Content", ColumnType.TXT);
  public static Column HEAD = new Column("Head", ColumnType.TXT100);
  public static Column GROUP_NAME = new Column("GroupName", ColumnType.TXT100);
  public static Column NAME = new Column("Name", ColumnType.TXT100);
  public static Column LIKE = new Column("Like", ColumnType.INT);
  public static Column TYPE_HG = new Column("TypeHG", ColumnType.TYPE_HG);
  public static Column TYPE_ML = new Column("TypeML", ColumnType.TYPE_ML);
  public static Column RECORD_ID = new Column("RecordId", ColumnType.TXT36);

  public static Table DOCUMENT = new Table("Document", new HashSet<Column>() {
    {
      add(DOC_ID);
      add(CONTENT);
      add(HEAD);
      add(LIKE);
      add(TYPE_ML);
    }
  });
  public static Table HEAD_GROUP = new Table("HeadGroup", new HashSet<Column>() {
    {
      add(RECORD_ID);
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

  public static HashSet<Column> COLUMNS = new HashSet<Column>() {
    {
      add(DOC_ID);
      add(CONTENT);
      add(HEAD);
      add(GROUP_NAME);
      add(NAME);
      add(LIKE);
      add(TYPE_HG);
      add(TYPE_ML);
    }
  };

  public static boolean hasColumn(Table table, Column column) {
    return table.columns.contains(column);
  }

  public static boolean isValidValue(JSON query) {
    if (query == null) {
      return false;
    }

    for (var entry : query.entrySet()) {
      ColumnType type = null;
      for (var col : COLUMNS) {
        if (col.name.equals(entry.getKey())) {
          type = col.type;
          break;
        }
      }
      if (type == null) {
        return false;
      }
      final var value = entry.getValue();
      switch (type) {
        case ColumnType.INT:
          try {
            Integer.parseInt(value);
            return true;
          } catch (NumberFormatException e) {
            return false;
          }
        case ColumnType.TXT:
          return true;
        case ColumnType.TXT100:
          return value.length() <= 100;
        case ColumnType.TXT36:
          return value.length() <= 36;
        case ColumnType.TYPE_HG:
          return value.equals("H") || value.equals("G");
        case ColumnType.TYPE_ML:
          return value.equals("M") || value.equals("L");
        default:
          return false;
      }
    }
    return true;
  }
}
