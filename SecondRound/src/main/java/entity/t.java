package entity;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

public class t {
    public static void main(String[] args) {
        Map<String,Object> t= new HashMap<>();
        t.put("1",1);
        t.put("2",2);
        t.put("3",3);
        String jsonString = JSON.toJSONString(t);
        System.out.println(jsonString);
    }
}
