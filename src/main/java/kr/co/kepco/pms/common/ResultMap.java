package kr.co.kepco.pms.common;

import org.apache.commons.collections4.map.ListOrderedMap;

public class ResultMap extends ListOrderedMap {

    public Object put(Object key, Object value) {
        return super.put(CamelUtil.convert2CamelCase((String) key), value);
    }
}
