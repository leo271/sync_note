package backend.model;

public enum NameSpaceCols implements DBCols {
  NAME("Name"), Type("Type");

  private final String name;

  private NameSpaceCols(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
