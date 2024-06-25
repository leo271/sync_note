package model;

import java.util.HashMap;

public class JSON extends HashMap<String, String> {
  public static JSON single(String key, String value) {
    var json = new JSON();
    json.put(key, value);
    return json;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof JSON) {
      var json = (JSON) obj;
      return this.entrySet().equals(json.entrySet());
    }
    return false;
  }
}
