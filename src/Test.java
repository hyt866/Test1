import com.squareup.okhttp.*;
import com.sun.media.jfxmedia.Media;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tony on 9/22/16.
 */
public class Test {
    public static void main(String args[]) {
        System.out.println("Hello World");

        OkHttpClient client = new OkHttpClient();
        client.setFollowSslRedirects(true);

        CookieManager cookieManager = new CookieManager();
        CookieHandler.setDefault(cookieManager);
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        client.setCookieHandler(cookieManager);

        String model = "MN8U2ZP";
        String store = "673";
        //String url = "https://reserve-hk.apple.com/HK/zh_HK/reserve/iPhone?partNumber=" + model + "%2FA&channel=1&rv=&path=&sourceID=&iPP=false&appleCare=&iUID=&iuToken=&carrier=&store=R" + store;
        String url = "https://reserve-hk.apple.com/HK/zh_HK/reserve/iPhone?partNumber=MN8K2ZP%2FA&channel=1&rv=&path=&sourceID=&iPP=false&appleCare=&iUID=&iuToken=&carrier=&store=R499";
        //String url = "https://signin.apple.com/IDMSWebAuth/signin?path=%2FHK%2Fzh_HK%2Freserve%2FiPhone%3Fexecution%3De1s1%26p_left%3DAAAAAASLIrddmxUwDKg1plQYomMpgo0JaZW3EBjYF%252B3KgzXKbQ%253D%253D%26_eventId%3Dnext&p_time=1474626323&rv=3&language=HK-ZH&p_left=AAAAAASLIrddmxUwDKg1plQYomMpgo0JaZW3EBjYF%2B3KgzXKbQ%3D%3D&appIdKey=db0114b11bdc2a139e5adff448a1d7325febef288258f0dc131d6ee9afe63df3";

        String result = null;
        Request request = new Request.Builder().url(url).build();

        try {
            Response response = client.newCall(request).execute();
            result = response.request().url().toString();
            //result = response.body().string();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        String path = result.substring(result.indexOf("?")+1,result.indexOf("&")).replace("path=","");

        System.out.println(path);

        System.out.println(result);
        // https://secure2.store.apple.com/hk-zh/shop/sign_in?
        // c=aHR0cDovL3d3dy5hcHBsZS5jb20vaGsvaXBob25lLTcvfDFhb3NiOTY4MzYxZTUyNDUyMTAxNGM5YmI2N2IwNjljYjEzNmJmYzMxMmQ1&
        // r=SCDHYHP7CY4H9XK2H&
        // s=aHR0cDovL3d3dy5hcHBsZS5jb20vaGsvaXBob25lLTcvfDFhb3NiOTY4MzYxZTUyNDUyMTAxNGM5YmI2N2IwNjljYjEzNmJmYzMxMmQ1


        try {
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON,"{\"accountName\":\"today20150919@gmail.com\",\"password\":\"Fuck9394\",\"rememberMe\":false}");
            request = new Request.Builder()
                    .url("https://signin.apple.com/appleauth/auth/signin")
                    .post(body)
                    .header("Accept","application/json, text/javascript, */*; q=0.01")
                    .header("X-Apple-Widget-Key","40692a3a849499c31657eac1ec8123aa")
                    .header("X-Apple-Locale","HK-ZH")
                    .build();
            Response response = client.newCall(request).execute();
            System.out.println(response.code());
            System.out.println(response.request().url().toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }


        try {
            FormEncodingBuilder builder = new FormEncodingBuilder();
            builder.add("rememberMe", "false");
            builder.add("oAuthToken", "");
            builder.add("appIdKey", "db0114b11bdc2a139e5adff448a1d7325febef288258f0dc131d6ee9afe63df3");
            builder.add("language", "HK-ZH");
            builder.add("path", URLDecoder.decode(path));
            //builder.add("path",path);
            builder.add("rv", "3");


            request = new Request.Builder()
                    .url("https://signin.apple.com/IDMSWebAuth/signin")
                    .post(builder.build())
                    .header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                    .build();

            Response response = client.newCall(request).execute();
            System.out.println(response.code());
            System.out.println(response.request().url().toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
