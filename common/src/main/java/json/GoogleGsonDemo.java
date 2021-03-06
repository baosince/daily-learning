package json;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import json.bean.BuildBean;
import json.bean.Group;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yidxue
 */
public class GoogleGsonDemo {

    public static void main(String[] args) {
        Group group = BuildBean.basicFunc();
        Gson gson = new Gson();
        // class to json
        String jsonStr = gson.toJson(group);
        System.out.println("class to json:");
        System.out.println(jsonStr);
        System.out.println("=============================");

        // json to class
        Group group1 = gson.fromJson(jsonStr, Group.class);
        System.out.println("json to class:");
        System.out.println(group1.toString());
        System.out.println("=============================");

        // get json specified key
        JsonObject jobj = new Gson().fromJson(jsonStr, JsonObject.class);
        System.out.println("get json value:");
        System.out.println(jobj.get("users"));
        System.out.println("=============================");

        // map to json
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("name", "erwin");
        dataMap.put("gender", "male");
        System.out.println("map to json:");
        String mapStr = gson.toJson(dataMap);
        System.out.println(mapStr);
        System.out.println("=============================");

        // json to map
        HashMap map = gson.fromJson(mapStr, HashMap.class);
        System.out.println("json to map:");
        System.out.println(map);
        System.out.println(map != null);
        System.out.println("=============================");

        // extract value from json string by key
        String json = "{ \"message\": {\"this\": 174065352, \"that\":\"ddd\"} }";
        JsonObject jobj1 = new Gson().fromJson(json, JsonObject.class);
        System.out.println(jobj1.get("message").getAsJsonObject().get("this"));
        System.out.println(jobj1.get("message").getAsJsonObject().get("that"));
    }
}