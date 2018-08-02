package org.wjh.androidlib.nohttp;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者： macpro  on 2018/6/17.
 * 邮箱： xxx.com
 */
public class HeaderParams {

    private Map<String, String> map = new HashMap<String, String>();

    private HeaderParams() {
    }

    public static HeaderParams create() {
        return new HeaderParams();
    }

    public static HeaderParams create(String key, String val) {
        HeaderParams hp = new HeaderParams();
        hp.map.put(key, val);
        return hp;
    }


    public HeaderParams put(String key, String val) {
        this.map.put(key, val);
        return this;
    }

    public Map<String, String> params() {
        return map;
    }

}
