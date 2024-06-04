package backend.model;

import java.io.InvalidObjectException;
import java.util.HashMap;

public enum HeadGroupCols implements DBCols {
  NAME("Name"), CHILD("Child"), Type("Type");

  private final String name;

  private HeadGroupCols(String name) {
    this.name = name;
  }

  @Override
  public String getName() {
    return name;
  }

  static public DBStr intoDB(HashMap<HeadGroupCols, String> args) throws InvalidObjectException {
    if (args.size() != 3) {
      throw new InvalidObjectException("Invalid number of arguments");
    }
    return new DBStr(
        String.format("%s\ts,%s\ts,%s\ts", args.get(NAME), args.get(CHILD), args.get(Type)));
  }
}
