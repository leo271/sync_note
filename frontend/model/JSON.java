package model;

import java.util.HashMap;

public class JSON extends HashMap<String, String> {
  public static JSON single(String key, String value) {
    var json = new JSON();
    json.put(key, value);
    return json;
  }
}
