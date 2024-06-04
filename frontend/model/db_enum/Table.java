package model.db_enum;

import java.util.HashSet;

public class Table {
  public String name;
  public HashSet<Column> columns;

  public Table(String name, HashSet<Column> columns) {
    this.name = name;
  }
}
