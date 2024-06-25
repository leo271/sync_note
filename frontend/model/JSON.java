package model;

import java.util.HashMap;

public class JSON extends HashMap<String, String> {
  public static JSON single(String key, String value) {
    var json = new JSON();
    json.put(key, value);
    return json;
  }

  public Entry<String, String>[] toEntries() {
    @SuppressWarnings("unchecked")
    Entry<String, String>[] entries = new Entry[this.size()];
    var i = 0;
    for (var entry : this.entrySet()) {
      entries[i] = entry;
      i++;
    }
    return entries;
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
