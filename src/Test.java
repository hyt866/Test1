import com.google.gson.Gson;
import com.squareup.okhttp.*;

import java.net.*;


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

        Request request = null;
        Response response = null;
        FormEncodingBuilder builder = null;
        Gson gson = new Gson();
        String url = null;
        String result = null;
        String path = null;
        String appIdKey = null;
        String smsJson = null;

        try {

            String model = "MN8K2ZP";
            //String model = "MN4L2ZP";
            String store = "673";
            url = "https://reserve-hk.apple.com/HK/zh_HK/reserve/iPhone?partNumber=" + model + "%2FA&channel=1&rv=&path=&sourceID=&iPP=false&appleCare=&iUID=&iuToken=&carrier=&store=R" + store;

            request = new Request.Builder()
                    .url(url)
                    .build();

            response = client.newCall(request).execute();
            result = response.request().url().toString();

            System.out.printf("%s: %s\n","Step1",url);
            path = result.substring(result.indexOf("signin?path=")+12,result.indexOf("&p_time="));
            appIdKey = result.substring(result.indexOf("&appIdKey=")+10);

            System.out.printf("%s\n\n",response.networkResponse().toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        try {

            MediaType JSON = MediaType.parse("application/json; charset=utf-8");

            String jsonString = "{\"accountName\":\"today20150919@gmail.com\",\"password\":\"Fuck9394\",\"rememberMe\":false}";
            RequestBody body = RequestBody.create(JSON,jsonString);
            url = "https://signin.apple.com/appleauth/auth/signin";

            request = new Request.Builder()
                    .url(url)
                    .header("X-Apple-Locale","HK-ZH")
                    .header("X-Apple-App-Id","942")
                    .header("X-Apple-Widget-Key","40692a3a849499c31657eac1ec8123aa")
                    .header("X-Apple-I-FD-Client-Info","{\"U\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.116 Safari/537.36\",\"L\":\"zh-TW\",\"Z\":\"GMT+08:00\",\"V\":\"1.1\",\"F\":\"70a44j1e3NlY5BSo9z4ofjb75PaK4Vpjt.gEngMQEjZr_WhXTA2s.XTVV26y8GGEDd5ihORoVyFGh8cmvSuCKzIlnY6xljQlpRhQTPdTdfBcEPm8LKfAaZ4pAJZ7OQuyPBB2SCXw2SCVL6yXyjaY1WMsiZRPrwVL6tqAhbrmQmkqlE4Ww.GEFF0Yz3ccbbJYMLgiPFU77qZoOSix5ezdstlYysrhsuiLhjCJjJpaJ6hO3f9p_nH1uzjkI1myjaY1nhdywzb_GGEOpBSKxUC56MnGWpwoNSUC53ZXnN87gq1W_BKHTgg9TdQxQeLaD.SAxN4t1VKWZWuxbuJjkWiMgdVgEKXuZqlricCw.Tf5.EKWG.0rNtMQukAm4.f282p9dyaKyOWFQ_v9NA14WX3NqhyA_r_LwwKdBvpZfWfUXtStKjE4PIDzp8hyr1BNlr9.NlY5QB4bVNjMk.3vn\"}")
                    .post(body)
                    .build();
            response = client.newCall(request).execute();

            System.out.printf("Step2: %s\n",url);
            System.out.printf("%s\n\n",response.networkResponse().toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        try {
            builder = new FormEncodingBuilder();
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
                    .build();

            response = client.newCall(request).execute();

            System.out.printf("Step3: %s\n",url);
            System.out.printf("%s\n\n",response.networkResponse().toString());

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        try {
            url = "https://reserve-hk.apple.com/HK/zh_HK/reserve/iPhone?execution=e1s2&ajaxSource=true&_eventId=context";
            request = new Request.Builder().url(url)
                    .build();

            response = client.newCall(request).execute();

            System.out.printf("Step4: %s\n",url);
            System.out.printf("%s\n",response.networkResponse().toString());
            smsJson = response.body().string();

        }
        catch (Exception e) {
            e.printStackTrace();
        }


        try {
            Sms sms = gson.fromJson(smsJson,Sms.class);

            builder = new FormEncodingBuilder();

            builder.add("phoneNumber","59984289");
            builder.add("selectedCountryCode","852");
            builder.add("registrationCode","yy3l2xxv");
            builder.add("submit","");
            builder.add("_flowExecutionKey",sms.get_flowExecutionKey());
            builder.add("_eventId","next");
            builder.add("p_ie",sms.getP_ie());
            builder.add("dims","s0a44j1e3NlY5BSo9z4ofjb75PaK4Vpjt.gEngMQEjZr_WhXTA2s.XTVV26y8GGEDd5ihORoVyFGh8cmvSuCKzIlnY6xljQlpRhQTPdTdfBcEPm8LKfAaZ4pAJZ7OQuyPBB2SCXw2SCVL6yXyjaY1WMsiZRPrwVL6tqAhbrmQmkqlE4Ww.GEFF0Yz3ccbbJYMLgiPFU77qZoOSix5ezdstlYysrhsuiLhjCJjJpaJ6hO3f9p_nH1uzjkI1myjaY1rlbYSQn0jfsttJJIqSI6KUMnGWpwoNSUC56MnGW87gq1HACVcOI4J3c._BPzLu_dYV6HzL0TFc4NO7TjOy_Aw7Q_v9NA2pNidCgSv_2U.6elV2pNJF.SqDKqyg1wWF9kmFxHeUWJz15uVrAqJkL3A237lhQwMAj9htsfHOrf8M2Lz4mvmfTT9oaSzeIQxi3NlYid7lY5BqNAE.lTjV.8Wm");

            url = "https://reserve-hk.apple.com/HK/zh_HK/reserve/iPhone?execution=e1s2";

            request = new Request.Builder()
                    .url(url)
                    .post(builder.build())
                    .build();

            response = client.newCall(request).execute();

            System.out.printf("Step5: %s\n",url);
            System.out.printf("%s\n\n",response.networkResponse().toString());

        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }
}
