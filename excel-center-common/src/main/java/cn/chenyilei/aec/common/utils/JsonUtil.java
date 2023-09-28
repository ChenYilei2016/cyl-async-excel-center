package cn.chenyilei.aec.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * @author chenyilei
 * @date 2023/09/28 11:36
 */
public class JsonUtil {
    public static boolean isJson(String jsonString) {
        return JSON.isValid(jsonString);
    }

    public static String toJsonString(Object object) {
        return JSON.toJSONString(object);
    }

    public static <T> T toObject(String jsonString, Class<T> clazz) {
        return JSON.parseObject(jsonString, clazz);
    }

    public static <T> T toObject(Map jsonString, Class<T> clazz) {
        return JSON.parseObject(toJsonString(jsonString), clazz);
    }

    public static Map toMap(Object object) {
        return JSON.parseObject(toJsonString(object));
    }

    public static Map toMap(String jsonString) {
        return JSON.parseObject(jsonString);
    }


    public static <T> List<T> toArrayObject(String jsonString, Class<T> clazz) {
        return JSON.parseArray(jsonString, clazz);
    }

    public static String merge(String oldJson, String newJson) {
        JSONObject oldJsonObj = JSON.parseObject(oldJson);
        if (oldJsonObj == null) {
            oldJsonObj = new JSONObject();
        }
        JSONObject newJsonObj = JSON.parseObject(newJson);
        if (newJsonObj == null) {
            return oldJsonObj.toJSONString();
        }
        oldJsonObj.putAll(newJsonObj);
        return oldJsonObj.toJSONString();
    }
}
