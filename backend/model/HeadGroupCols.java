package backend.model;

public enum HeadGroupCols implements DBCols {
  NAME("Name"), CHILD("Child"), Type("Type");

  private final String name;

  private HeadGroupCols(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
