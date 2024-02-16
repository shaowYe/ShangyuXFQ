package org.example;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {


    private static final String xfq20 = "266";
    private static final String xfq50 = "269";
    private static final String xfq100 = "272";


    //ysw
    private static final String accountId = "65163191ce20a304691e73a4";
    private static final String sessionId = "65c1a19e7dee053d969742d1";



    //llq
    //    private static final String accountId = "65c45beedc0c8f04ee764a34";
    //    private static final String sessionId = "65c45beedc0c8f04ee764a35";




    private static final String loginUrl = "http://www.ishangyu.net/yx/h5/app2.0/data/login.php";

    private static final String xfqUrl = "http://www.ishangyu.net/yx/h5/app2.0/xfq2024/ajax.php";

    private static final String recordUrl = "http://www.ishangyu.net/yx/h5/app2.0/xfq/my_record.php";

    public static void main(String[] args) throws InterruptedException {

        while (true) {
            System.out.println("开始获取 cookie~~~~");
            String cookie = login();
            if (cookie.isEmpty()) {
                System.out.println("获取 cookie 失败啦");
                continue;
            }
            System.out.println("开始获取 抢100~~~~ cookie:" + cookie);
            ExecutorService executor100 = Executors.newFixedThreadPool(50); // 并发调用 20 次，根据需要设置线程池大小
            for (int i = 0; i < 20; i++) {
                executor100.execute(() -> {
                    while (true) {
                        try {
                            boolean xfq = xfq(cookie,xfq100);
                            if (xfq) {
                                System.exit(0);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

            System.out.println("开始获取 抢50~~~~ cookie:" + cookie);
            ExecutorService executor50 = Executors.newFixedThreadPool(50); // 并发调用 20 次，根据需要设置线程池大小
            for (int i = 0; i < 20; i++) {
                executor50.execute(() -> {
                    while (true) {
                        try {
                            boolean xfq = mockXfq(cookie,xfq50);
                            if (xfq) {
                                System.exit(0);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }


            System.out.println("开始获取 抢20~~~~ cookie:" + cookie);
            ExecutorService executor20 = Executors.newFixedThreadPool(50); // 并发调用 20 次，根据需要设置线程池大小
            for (int i = 0; i < 20; i++) {
                executor20.execute(() -> {
                    while (true) {
                        try {
                            boolean xfq = mockXfq(cookie,xfq20);
                            if (xfq) {
                                System.exit(0);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }


    }


    public static class Action implements Runnable {

        public Action(String cookie) {
            this.cookie = cookie;
        }


        private String cookie;

        @Override
        public void run() {
            System.out.println(cookie);
        }
    }


    public static boolean mockXfq(String cookie, String xfq){
        System.out.println("mock xfq:"+xfq);
        return false;
    }

    public static boolean xfq(String cookie, String xfq) {

        OkHttpClient client = new OkHttpClient();
        Headers headers = new Headers.Builder()
                .add("Host", "www.ishangyu.net")
                .add("X-Requested-With", "XMLHttpRequest")
                .add("Accept-Encoding", "gzip, deflate")
                .add("Accept-Language", "zh-CN,zh-Hans;q=0.9")
                .add("Accept", "application/json, text/javascript, */*; q=0.01")
                .add("Origin", "http://www.ishangyu.net")
                .add("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 17_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148;;xsb;xsb_shangyu;2.1.6;Appstore;native_app")
                .add("Referer", "http://www.ishangyu.net/yx/h5/app2.0/xfq2024/")
                .add("Cookie", cookie)
                .add("Connection", "keep-alive")
                .build();


        RequestBody formBody = new FormBody.Builder()
                .add("a", xfq)
                .build();

        Request recordRequest = new Request.Builder()
                .url(xfqUrl)
                .headers(headers)
                .post(formBody)
                .build();
        try {
            Response response = client.newCall(recordRequest).execute();

            if (response.isSuccessful()) {
                // 打印响应体
                if (response.body() != null) {
                    String repStr = response.body().string();
                    System.out.println("body: " + repStr);
                    JSONObject jsonObject = JSON.parseObject(repStr);
                    if ("1".equals(jsonObject.getString("flag"))) {
                        System.out.println("抢到了!!!");
                        return true;
                    }
                    if ("3".equals(jsonObject.getString("flag"))) {
                        System.out.println("已经有了 不抢啦!!!");
                        return true;
                    }
                } else {
                    System.out.println("body 为空");
                }
            } else {
                // 处理错误
                System.out.println("请求失败: " + response.code() + " " + response.message());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    //
//    Host	www.ishangyu.net
//    X-Requested-With	XMLHttpRequest
//    Accept-Encoding	gzip, deflate
//    Accept-Language	zh-CN,zh-Hans;q=0.9
//    Accept	application/json, text/javascript, */*; q=0.01
//    Origin	http://www.ishangyu.net
//    Content-Length	0
//    User-Agent	Mozilla/5.0 (iPhone; CPU iPhone OS 17_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148;;xsb;xsb_shangyu;2.1.6;Appstore;native_app
//    Referer	http://www.ishangyu.net/yx/h5/app2.0/xfq/form.html
//    Cookie	PHPSESSID=n0j00fg3fgn93l6ch06up9vhk1
//    Connection	keep-alive
    public static void record(String cookie) {
        OkHttpClient client = new OkHttpClient();
        Headers headers = new Headers.Builder()
                .add("Host", "www.ishangyu.net")
                .add("X-Requested-With", "XMLHttpRequest")
                .add("Accept-Encoding", "gzip, deflate")
                .add("Accept-Language", "zh-CN,zh-Hans;q=0.9")
                .add("Accept", "application/json, text/javascript, */*; q=0.01")
                .add("Origin", "http://www.ishangyu.net")
                .add("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 17_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148;;xsb;xsb_shangyu;2.1.6;Appstore;native_app")
                .add("Referer", "http://www.ishangyu.net/yx/h5/app2.0/xfq2024/")
                .add("Cookie", cookie)
                .add("Connection", "keep-alive")
                .build();
        Request recordRequest = new Request.Builder()
                .url(recordUrl)
                .headers(headers)
                .post(RequestBody.create(new byte[0], null))
                .build();
        try {
            Response response = client.newCall(recordRequest).execute();

            if (response.isSuccessful()) {
                // 打印响应体
                System.out.println("body: " + response.body().string());

            } else {
                // 处理错误
                System.out.println("请求失败: " + response.code() + " " + response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    // login 请求的 header
//    Host	www.ishangyu.net
//    Accept	application/json, text/javascript, */*; q=0.01
//    X-Requested-With	XMLHttpRequest
//    Accept-Language	zh-CN,zh-Hans;q=0.9
//    Accept-Encoding	gzip, deflate
//    Content-Type	application/x-www-form-urlencoded; charset=UTF-8
//    Origin	http://www.ishangyu.net
//    User-Agent	Mozilla/5.0 (iPhone; CPU iPhone OS 17_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148;;xsb;xsb_shangyu;2.1.6;Appstore;native_app
//    Referer	http://www.ishangyu.net/yx/h5/app2.0/xfq/form.html
//    Content-Length	69
//    Connection	keep-alive
    public static String login() {
        OkHttpClient client = new OkHttpClient();
        //设置 header
        Headers headers = new Headers.Builder()
                .add("Host", "www.ishangyu.net")
                .add("Accept", "application/json, text/javascript, */*; q=0.01")
                .add("X-Requested-With", "XMLHttpRequest")
                .add("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .add("Accept-Language", "zh-CN,zh-Hans;q=0.9")
                .add("Accept-Encoding", "gzip, deflate")
                .add("Origin", "http://www.ishangyu.net")
                .add("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 17_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148;;xsb;xsb_shangyu;2.1.6;Appstore;native_app")
                .add("Referer", "http://www.ishangyu.net/yx/h5/app2.0/xfq/form.html")
                .add("Connection", "keep-alive")
                .build();

        RequestBody formBody = new FormBody.Builder()
                .add("accountId", accountId)
                .add("sessionId", sessionId)
                .build();

        Request loginRequest = new Request.Builder()
                .url(loginUrl)
                .headers(headers)
                .post(formBody).build();
        try {
            Response response = client.newCall(loginRequest).execute();

            if (response.isSuccessful()) {
                // 打印响应体
                System.out.println("body: " + response.body().string());
                String cookie = response.headers().get("Set-Cookie");
                cookie = getCookie(cookie);
                System.out.println("cookie: " + cookie);
                return cookie;

            } else {
                // 处理错误
                System.out.println("请求失败: " + response.code() + " " + response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getCookie(String setCookie) {
        try {
            String[] split = setCookie.split(";");
            return split[0].trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }


}