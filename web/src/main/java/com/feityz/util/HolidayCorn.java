package com.feityz.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.codehaus.jackson.JsonParser;
import springfox.documentation.spring.web.json.Json;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 节假日获取
 */
public class HolidayCorn {

    public void initHolidyThisYear(){
        
        String url = "http://v.juhe.cn/calendar/month";

        String key = "3177ba501d256d04a4fd68f397c3f799";

        Map params = new HashMap();

        params.put("key",key);

        params.put("year-month","2020-1");

        String result = HttpUtil.post(url,params);

        System.out.printf(result);

    }

    public static void main(String[] args) {

        String url = "http://v.juhe.cn/calendar/month";

        String key = "3177ba501d256d04a4fd68f397c3f799";

        Map params = new HashMap();

        params.put("key", key);

        params.put("year-month", "2020-1");

        String resultStr = HttpUtil.post(url, params);

        JSONObject jsonObject = JSONUtil.parseObj(resultStr);

        JSONObject result = (JSONObject)jsonObject.get("result");

        JSONObject data = (JSONObject)result.get("data");

        JSONArray holidayArray = data.getJSONArray("holiday_array");

        holidayArray.forEach(e->{

            JSONArray list = ((JSONObject)e).getJSONArray("list");

            for (Object obj:list) {
                JSONObject date = (JSONObject) obj;
                System.out.println(date.get("date"));
                String datestr = date.get("date").toString();
                Date holidy = DateUtil.parse(datestr,"yyyy-MM-dd");
            }
        });

        //System.out.printf(result);
    }

}
