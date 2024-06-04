package backend.model;

import java.io.InvalidObjectException;
import java.util.HashMap;

public enum DocumentCols implements DBCols {
  DOC_ID("DocID"), CONTENT("Content"), HEAD("Head"), LIKE("Like");

  private final String name;

  private DocumentCols(String name) {
    this.name = name;
  }

  @Override
  public String getName() {
    return name;
  }

  static public DBStr intoDB(HashMap<DocumentCols, String> args) throws InvalidObjectException {
    if (args.size() != 4) {
      throw new InvalidObjectException("Invalid number of arguments");
    }
    return new DBStr(String.format("%s\ts,%s\ts,%s\ts,%s\td", args.get(DOC_ID), args.get(CONTENT),
        args.get(HEAD), args.get(LIKE)));
  }
}
