import com.squareup.okhttp.*;

import java.net.*;
import java.util.List;


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

        Request request = null;
        Response response = null;
        String url = null;
        String result = null;
        String path = null;
        String appIdKey = null;
        String referer = null;

        try {

            MediaType JSON = MediaType.parse("application/json; charset=utf-8");

            String jsonString = "{\"accountName\":\"today20150919@gmail.com\",\"password\":\"Fuck9394\",\"rememberMe\":false}";
            RequestBody body = RequestBody.create(JSON,jsonString);
            url = "https://signin.apple.com/appleauth/auth/signin?widgetKey=40692a3a849499c31657eac1ec8123aa&language=HK-ZH";

            request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .header("Accept","application/json, text/javascript, */*; q=0.01")
                    .header("Accept-Language","en-US,en;q=0.5")
                    .header("Accept-Encoding","gzip, deflate, br")
                    .header("Content-Type","application/json")
                    .header("X-Apple-Widget-Key","40692a3a849499c31657eac1ec8123aa")
                    .header("X-Apple-Locale","HK-ZH")
                    .build();
            response = client.newCall(request).execute();

            System.out.printf("Step1: %s\n",url);
            System.out.printf("%s\n\n",response.networkResponse().toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        try {

            String model = "MN8K2ZP";
            String store = "673";
            url = "https://reserve-hk.apple.com/HK/zh_HK/reserve/iPhone?partNumber=" + model + "%2FA&channel=1&rv=&path=&sourceID=&iPP=false&appleCare=&iUID=&iuToken=&carrier=&store=R" + store;

            request = new Request.Builder()
                    .url(url)
                    .header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                    .header("Accept-Language","zh-HK,en-US;q=0.7,en;q=0.3")
                    .header("Accept-Encoding","gzip, deflate, br")
                    .header("Connection","keep-alive")
                    .header("Upgrade-Insecure-Requests","1")
                    .build();

            response = client.newCall(request).execute();
            result = response.request().url().toString();
            referer = result;

            System.out.printf("%s: %s\n","Step2",url);
            path = result.substring(result.indexOf("signin?path=")+12,result.indexOf("&p_time="));
            appIdKey = result.substring(result.indexOf("&appIdKey=")+10);

            System.out.printf("path  : %s\n",path);
            System.out.printf("decode: %s\n",URLDecoder.decode(path));
            System.out.printf("appIdKey: %s\n",appIdKey);
            System.out.printf("%s\n\n",response.networkResponse().toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        try {
            FormEncodingBuilder builder = new FormEncodingBuilder();
            builder.add("rememberMe", "false");
            builder.add("oAuthToken", "");
            builder.add("appIdKey", appIdKey);
            builder.add("language", "HK-ZH");
            builder.add("path", URLDecoder.decode(path));
            builder.add("rv", "3");

            url = "https://signin.apple.com/IDMSWebAuth/signin";

            request = new Request.Builder()
                    .url(url)
                    .post(builder.build())
                    .header("Referer",referer)
                    .header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                    .header("Accept-Language","zh-HK,en-US;q=0.7,en;q=0.3")
                    .header("Accept-Encoding","gzip, deflate, br")
                    .header("Connection","keep-alive")
                    .header("Upgrade-Insecure-Requests","1")
                    .header("Content-Type","application/x-www-form-urlencoded")
                    .build();

            response = client.newCall(request).execute();

            System.out.printf("Step3: %s\n",url);
            Response prior3 = response.priorResponse();
            Response prior2 = prior3.priorResponse();
            Response prior1 = prior2.priorResponse();
            System.out.printf("prior1: %s\n",prior1.toString());
            System.out.printf("prior2: %s\n",prior2.toString());
            System.out.printf("prior3: %s\n",prior3.toString());
            System.out.printf("%s\n\n",response.networkResponse().toString());
            List<HttpCookie> cookies = cookieManager.getCookieStore().getCookies();
            System.out.println(cookies);

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        url = "https://reserve-hk.apple.com/HK/zh_HK/reserve/iPhone?execution=e1s2&ajaxSource=true&_eventId=context";
        request = new Request.Builder().url(url)
                .header("Accept","application/json, text/javascript, */*; q=0.01")
                .header("Accept-Language","en-US,en;q=0.5")
                //.header("Accept-Encoding","gzip, deflate, br")
                .header("X-Requested-With","XMLHttpRequest")
                .header("Connection","keep-alive")
                .header("Referer","https://reserve-hk.apple.com/HK/zh_HK/reserve/iPhone?execution=e1s2")
                .build();

        try {
            response = client.newCall(request).execute();

            System.out.printf("Step4: %s\n",url);
            System.out.printf("%s\n",response.networkResponse().toString());

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
