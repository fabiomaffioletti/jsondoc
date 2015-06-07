package org.jsondoc.core.pojo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bsmk on 6/7/15.
 */
public class JSONDocTemplate {

  private final Map<String, Object> map = new HashMap<String, Object>();

  public void put(String key, Object value) {
    map.put(key, value);
  }

  public Map<String, Object> getMap() {
    return map;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    JSONDocTemplate that = (JSONDocTemplate) o;

    return map.equals(that.map);

  }

  @Override
  public int hashCode() {
    return map.hashCode();
  }
}
