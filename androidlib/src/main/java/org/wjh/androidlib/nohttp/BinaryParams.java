package org.wjh.androidlib.nohttp;


import com.yanzhenjie.nohttp.Binary;

import java.util.LinkedHashMap;
import java.util.Map;

public class BinaryParams {

    private Map<String, Binary> map = new LinkedHashMap<>();

    public BinaryParams() {
    }

    public static BinaryParams create(String key, Binary val) {
        BinaryParams hp = new BinaryParams();
        hp.map.put(key, val);
        return hp;
    }


    public BinaryParams put(String key, Binary val) {
        this.map.put(key, val);
        return this;
    }

    public Map<String, Binary> params() {
        return map;
    }

}
