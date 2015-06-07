package org.jsondoc.core.pojo;

import java.util.*;

/**
 * Created by bsmk on 6/7/15.
 */
public class JSONDocTemplate {

  private final Map<NameOrder, Object> map = new TreeMap<NameOrder, Object>(new Comparator<NameOrder>() {
    @Override
    public int compare(NameOrder o1, NameOrder o2) {
      if (o1.order == o2.order) {
        return o1.name.compareTo(o2.name);
      }
      return o1.order - o2.order;
    }
  });

  /**
   * Puts entry to map. Order is also taken into account in generating final map.
   */
  public void put(String name, int order, Object value) {
    map.put(new NameOrder(name, order), value);
  }

  public Map<String, Object> getMap() {
    final Map<String, Object> oldMap = new LinkedHashMap<String, Object>();
    for (Map.Entry<NameOrder, Object> entry : map.entrySet()) {
      oldMap.put(entry.getKey().name, entry.getValue());
    }
    return oldMap;
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


  public static final class NameOrder {
    private final String name;
    private final int order;

    public NameOrder(String name, int order) {
      this.name = name;
      this.order = order;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      NameOrder nameOrder = (NameOrder) o;

      if (order != nameOrder.order) return false;
      return name.equals(nameOrder.name);

    }

    @Override
    public int hashCode() {
      int result = name.hashCode();
      result = 31 * result + order;
      return result;
    }
  }

}
