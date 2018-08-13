package org.wjh.androidlib.nohttp;


import com.yanzhenjie.nohttp.FileBinary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BinaryParams {

    private List<Map<String, FileBinary>> binarys = new ArrayList<>();
    private long binarysLength = 0;

    private BinaryParams() {
    }

    public static BinaryParams create() {
        BinaryParams hp = new BinaryParams();
        return hp;
    }

    public static BinaryParams create(String key, FileBinary val) {
        BinaryParams hp = new BinaryParams();
        Map<String, FileBinary> map = new HashMap<>();
        map.put(key, val);
        hp.binarys.add(map);
        hp.binarysLength += val.getBinaryLength();
        return hp;
    }


    public BinaryParams put(String key, FileBinary val) {
        Map<String, FileBinary> map = new HashMap<>();
        map.put(key, val);
        this.binarys.add(map);
        this.binarysLength += val.getBinaryLength();
        return this;
    }

    public List<Map<String, FileBinary>> params() {
        return binarys;
    }

    public long getBinarysLength() {
        return binarysLength;
    }
}
