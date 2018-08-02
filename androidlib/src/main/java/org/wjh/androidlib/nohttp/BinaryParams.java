package org.wjh.androidlib.nohttp;


import com.yanzhenjie.nohttp.Binary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BinaryParams {

    private List<Map<String, Binary>> binarys = new ArrayList<>();

    private BinaryParams() {
    }

    public static BinaryParams create() {
        BinaryParams hp = new BinaryParams();
        return hp;
    }

    public static BinaryParams create(String key, Binary val) {
        BinaryParams hp = new BinaryParams();
        Map<String, Binary> map = new HashMap<>();
        map.put(key, val);
        hp.binarys.add(map);
        return hp;
    }


    public BinaryParams put(String key, Binary val) {
        Map<String, Binary> map = new HashMap<>();
        map.put(key, val);
        this.binarys.add(map);
        return this;
    }

    public List<Map<String, Binary>> params() {
        return binarys;
    }

}
