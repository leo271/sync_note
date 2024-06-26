package model.db_enum;

public class Column {
  public String name;
  public ColumnType type;

  public Column(String name, ColumnType type) {
    this.name = name;
    this.type = type;
  }
}

