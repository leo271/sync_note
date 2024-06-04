package backend.model;

public class Column {
  public String name;
  public ColumnType type;
  public String value;

  public Column(String name, ColumnType type) {
    this.name = name;
    this.type = type;
    this.value = null;
  }

  public Column(String name, ColumnType type, String value) {
    this.name = name;
    this.type = type;
    this.value = value;
  }
}

