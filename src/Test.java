import com.squareup.okhttp.*;
import org.json.JSONObject;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URLDecoder;


/**
 * Created by tony on 9/22/16.
 */
public class Test {
    public static void main(String args[]) {
        System.out.println("Hello World");

        OkHttpClient client = new OkHttpClient();
        client.setFollowSslRedirects(true);
        client.setFollowRedirects(true);

        CookieManager cookieManager = new CookieManager();
        CookieHandler.setDefault(cookieManager);
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        client.setCookieHandler(cookieManager);

        String model = "MN8K2ZP";
        String store = "499";
        String url = "https://reserve-hk.apple.com/HK/zh_HK/reserve/iPhone?partNumber=" + model + "%2FA&channel=1&rv=&path=&sourceID=&iPP=false&appleCare=&iUID=&iuToken=&carrier=&store=R" + store;
        //String url = "https://reserve-hk.apple.com/HK/zh_HK/reserve/iPhone?partNumber=MN8K2ZP%2FA&channel=1&rv=&path=&sourceID=&iPP=false&appleCare=&iUID=&iuToken=&carrier=&store=R499";
        //String url2 = "https://signin.apple.com/IDMSWebAuth/signin?path=%2FHK%2Fzh_HK%2Freserve%2FiPhone%3Fexecution%3De1s1%26p_left%3DAAAAAASLIrddmxUwDKg1plQYomMpgo0JaZW3EBjYF%252B3KgzXKbQ%253D%253D%26_eventId%3Dnext&p_time=1474626323&rv=3&language=HK-ZH&p_left=AAAAAASLIrddmxUwDKg1plQYomMpgo0JaZW3EBjYF%2B3KgzXKbQ%3D%3D&appIdKey=db0114b11bdc2a139e5adff448a1d7325febef288258f0dc131d6ee9afe63df3";

        String result = null;
        Request request = new Request.Builder().url(url).build();
        Response response = null;

        try {
            response = client.newCall(request).execute();
            result = response.request().url().toString();
            System.out.println(result);
            System.out.println(response.networkResponse().toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        String path = result.substring(result.indexOf("signin?path=")+12,result.indexOf("&p_time="));
        String appIdKey = result.substring(result.indexOf("&appIdKey=")+10);

        System.out.println(result);
        System.out.println(path);
        System.out.println(URLDecoder.decode(path));
        System.out.println(appIdKey);



        try {

            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
/*
            JSONObject json = new JSONObject();
            json.put("accountName","today20150919@gmail.com");
            json.put("password","Fuck93");
            json.put("rememberMe",false);

            RequestBody body = RequestBody.create(JSON,json.toString());
*/
            String jsonString = "{\"accountName\":\"today20150919@gmail.com\",\"password\":\"Fuck9394\",\"rememberMe\":false}";
            RequestBody body = RequestBody.create(JSON,jsonString);

            request = new Request.Builder()
                    //.url("https://signin.apple.com/appleauth/auth/signin")
                    .url("https://signin.apple.com/appleauth/auth/signin?widgetKey=40692a3a849499c31657eac1ec8123aa&language=HK-ZH")
                    .post(body)
                    .header("Accept","application/json, text/javascript, */*; q=0.01")
                    .header("X-Apple-Widget-Key","40692a3a849499c31657eac1ec8123aa")
                    .header("X-Apple-Locale","HK-ZH")
                    .build();
            response = client.newCall(request).execute();
            System.out.println(response.code());
            System.out.println(response.request().url().toString());
            System.out.println(response.networkResponse().toString());

        }
        catch (Exception e) {
            e.printStackTrace();
        }


        try {
            path = path.replace("e1s1","e2s1");
            FormEncodingBuilder builder = new FormEncodingBuilder();
            builder.add("rememberMe", "false");
            builder.add("oAuthToken", "");
            builder.add("appIdKey", appIdKey);
            builder.add("language", "HK-ZH");
            builder.add("path", URLDecoder.decode(path));
            //builder.add("path",path);
            builder.add("rv", "3");

            //https://signin.apple.com/IDMSWebAuth/signin?path=%2FHK%2Fzh_HK%2Freserve%2FiPhone%3Fexecution%3De1s1%26p_left%3DAAAAAAS%252FYRBVeUW9NDfT%252FjCODh62gtRBmWOuqDsl93u0WWBsXw%253D%253D%26_eventId%3Dnext&p_time=1474785866&rv=3&language=HK-ZH&p_left=AAAAAAS%2FYRBVeUW9NDfT%2FjCODh62gtRBmWOuqDsl93u0WWBsXw%3D%3D&appIdKey=db0114b11bdc2a139e5adff448a1d7325febef288258f0dc131d6ee9afe63df3
            //https://signin.apple.com/IDMSWebAuth/signin?path=%2FHK%2Fzh_HK%2Freserve%2FiPhone%3Fexecution%3De1s2%26p_left%3DAAAAAATPKcW5m00W7gd8QIipT46PMDFtG7psz2hUx%252F5uO5XhBw%253D%253D%26_eventId%3Dnext&p_time=1474785870&rv=3&language=HK-ZH&p_left=AAAAAATPKcW5m00W7gd8QIipT46PMDFtG7psz2hUx%2F5uO5XhBw%3D%3D&appIdKey=db0114b11bdc2a139e5adff448a1d7325febef288258f0dc131d6ee9afe63df3
            //https://signin.apple.com/IDMSWebAuth/signin?path=%2FHK%2Fzh_HK%2Freserve%2FiPhone%3Fexecution%3De1s2%26p_left%3DAAAAAASbe0FbS%252BB8WoFEuSSyb7btDG8OFirbZC%252BybGYBde8VFg%253D%253D%26_eventId%3Dnext&p_time=1474786093&rv=3&language=HK-ZH&p_left=AAAAAASbe0FbS%2BB8WoFEuSSyb7btDG8OFirbZC%2BybGYBde8VFg%3D%3D&appIdKey=db0114b11bdc2a139e5adff448a1d7325febef288258f0dc131d6ee9afe63df3
            //https://signin.apple.com/IDMSWebAuth/signin?path=%2FHK%2Fzh_HK%2Freserve%2FiPhone%3Fexecution%3De1s2%26p_left%3DAAAAAASXztkK8tGlH7VhLoDcFfKU%252FZy%252B2q1Bf1hMn61lRcvmnw%253D%253D%26_eventId%3Dnext&p_time=1474786410&rv=3&language=HK-ZH&p_left=AAAAAASXztkK8tGlH7VhLoDcFfKU%2FZy%2B2q1Bf1hMn61lRcvmnw%3D%3D&appIdKey=db0114b11bdc2a139e5adff448a1d7325febef288258f0dc131d6ee9afe63df3
            //https://signin.apple.com/IDMSWebAuth/signin?path=%2FHK%2Fzh_HK%2Freserve%2FiPhone%3Fexecution%3De1s2%26p_left%3DAAAAAARJqd%252BcEgVwmhR5z5ZlMl9suskEKUqc13Fidvpcs6uFTg%253D%253D%26_eventId%3Dnext&p_time=1474786608&rv=3&language=HK-ZH&p_left=AAAAAARJqd%2BcEgVwmhR5z5ZlMl9suskEKUqc13Fidvpcs6uFTg%3D%3D&appIdKey=db0114b11bdc2a139e5adff448a1d7325febef288258f0dc131d6ee9afe63df3

            request = new Request.Builder()
                    .url("https://signin.apple.com/IDMSWebAuth/signin")
                    .post(builder.build())
                    .header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                    .header("Accept-Language","zh-HK,en-US;q=0.7,en;q=0.3")
                    .header("Accept-Encoding","gzip, deflate, br")
                    .header("Connection","keep-alive")
                    .header("Upgrade-Insecure-Requests","1")
                    .header("Content-Type","application/x-www-form-urlencoded")
                    .build();

            response = client.newCall(request).execute();
            System.out.println(response.code());
            System.out.println(response.request().url().toString());
            System.out.println(response.networkResponse().toString());
            System.out.println(response.isRedirect());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        String e2s2 = "https://reserve-hk.apple.com/HK/zh_HK/reserve/iPhone?execution=e2s2&ajaxSource=true&_eventId=context";
        //String e2s2 = "https://reserve-hk.apple.com/HK/zh_HK/reserve/iPhone?execution=e2s2";
        request = new Request.Builder().url(e2s2).build();

        try {
            response = client.newCall(request).execute();
            result = response.request().url().toString();
            System.out.println("haha");
            System.out.println(response.body().string());
            System.out.println("haha2");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(response.code());
        System.out.println(result);
        System.out.println(response.networkResponse().toString());


    }
}
