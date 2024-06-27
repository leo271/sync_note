package model;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import model.db_enum.Column;

public class JSON extends ArrayList<Entry<Column, String>> {
  public static JSON single(Column key, String value) {
    var json = new JSON();
    json.put(key, value);
    return json;
  }

  public void put(Column key, String value) {
    this.add(Map.entry(key, value));
  }

  public String get(Column key) {
    for (var entry : this) {
      if (entry.getKey().equals(key)) {
        return entry.getValue();
      }
    }
    return null;
  }

  @Override
  public String toString() {
    var result = new StringBuilder();
    result.append("{\n");
    for (var entry : this) {
      result.append("\t").append(entry.getKey().name).append("\t: ").append(entry.getValue())
          .append("\n");
    }
    result.append("}\n");
    return result.toString();
  }
}
