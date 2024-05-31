package backend.model;

public enum Table {
  DOCUMENT("Document"), HEAD_GROUP("HeadGroup"), NAME_SPACE("NameSpace");

  private final String name;

  private Table(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
