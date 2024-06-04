package backend.model;

import java.io.InvalidObjectException;
import java.util.HashMap;

public enum NameSpaceCols implements DBCols {
  NAME("Name"), Type("Type");

  private final String name;

  private NameSpaceCols(String name) {
    this.name = name;
  }

  @Override
  public String getName() {
    return name;
  }

  static public DBStr intoDB(HashMap<NameSpaceCols, String> args) throws InvalidObjectException {
    if (args.size() != 2) {
      throw new InvalidObjectException("Invalid number of arguments");
    }
    return new DBStr(String.format("%s\ts,%s\ts", args.get(NAME), args.get(Type)));
  }
}
