package backend.model;

public interface DBCols {
  public String getName();

  class DBStr {
    public String str;

    public DBStr(String str) {
      this.str = str;
    }
  }
}
