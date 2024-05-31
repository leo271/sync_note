package backend.model;

public enum DocumentCols implements DBCols {
  DOC_ID("DocID"), CONTENT("Content"), HEAD("Head"), LIKE("Like");

  private final String name;

  private DocumentCols(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
