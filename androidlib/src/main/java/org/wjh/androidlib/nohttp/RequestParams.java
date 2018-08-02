package org.wjh.androidlib.nohttp;

import com.yanzhenjie.nohttp.Binary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者： macpro  on 2018/6/17.
 * 邮箱： xxx.com
 */
public class RequestParams {

    private Map<String, Object> map = new HashMap<String, Object>();

    private RequestParams() {
    }

    public static RequestParams create() {
        return new RequestParams();
    }

    public static RequestParams create(String key, Object val) {
        RequestParams hp = new RequestParams();
        hp.map.put(key, val);
        return hp;
    }

    public RequestParams put(String key, Object val) {
        this.map.put(key, val);
        return this;
    }

    public RequestParams putBinarys(String key, List<Binary> binaries) {
        this.map.put(key, binaries);
        return this;
    }

    public Map<String, Object> params() {
        return map;
    }

}
