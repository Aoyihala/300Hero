package com.example.evenalone.a300hero.utils;

import android.util.Log;
import android.widget.Toast;

import com.example.evenalone.a300hero.app.MyApplication;
import com.example.evenalone.a300hero.bean.GameInfo;
import com.example.evenalone.a300hero.bean.HeroGuide;
import com.example.evenalone.a300hero.bean.NetWorkProx;
import com.example.evenalone.a300hero.bean.YourRole;
import com.example.evenalone.a300hero.event.BindEvent;
import com.example.evenalone.a300hero.event.GameInfoEvent;
import com.example.evenalone.a300hero.event.ListInfoEvent;
import com.example.evenalone.a300hero.event.NetWorkCancelEvent;
import com.example.evenalone.a300hero.event.NetWorkEevent;
import com.example.evenalone.a300hero.event.ProxEvent;
import com.example.evenalone.a300hero.event.SProxyEvent;
import com.example.evenalone.a300hero.ui.SettingActivity;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.EventListener;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;

public class OkhttpUtils
{
    private static volatile OkhttpUtils instance;
    private OkHttpClient okHttpClient;
    private OkhttpUtils()
    {
        init();
    }
    private Call call;
    public void updateproxy(final int page, final Class c)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //访问网页
                try {
                    List<NetWorkProx> netWorkProxList = new ArrayList<>();
                    Document doc = Jsoup.connect(Contacts.IP_POOL+(page==0?"":page))
                            .timeout(5000) // 设置超时时间
                            .get(); // 使用GET方法访问URL
                    //访问节点
                    //去掉换行符
                    String html = doc.outerHtml().replace("\n","");
                    doc = Jsoup.parse(html);
                    Elements element = doc.select("table").select("tr");
                    for (int i=0;i<element.size();i++)
                    {
                        if (i>0) {
                            //自定越过第一个
                            String tes = element.get(i).text();
                            //解析xml
                            if (tes!=null)
                            {
                                String[] text = tes.split(" ");
                                /**
                                 * 1.ip地址
                                 * 2.端口号
                                 * 3.地址
                                 * 4.描述
                                 * 5.类型(http/https)
                                 * 6.time
                                 */
                                if (text!=null&&text.length>0)
                                {
                                    //赋值
                                    NetWorkProx prox = new NetWorkProx();

                                    //只保留http代理地址
                                    prox.setIp(text[0].trim());
                                    prox.setHost(text[1].trim());
                                    prox.setAdress(text[2].trim());
                                    prox.setDes(text[3].trim());
                                    prox.setType(text[4].trim());
                                    prox.setDay(text[5].trim());
                                    prox.setUpdate_time(text[6]);
                                    netWorkProxList.add(prox);

                                }
                            }
                        }

                    }
                    //临时代理ip缓存池子
                    if (c.getSimpleName().equals(SettingActivity.class.getSimpleName()))
                    {
                        EventBus.getDefault().post(new SProxyEvent(netWorkProxList));
                    }
                    else
                    {
                        EventBus.getDefault().post(new ProxEvent(netWorkProxList));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
    public void init() {
        //释放
        okHttpClient = null;
        //关闭代理
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10,TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .eventListener(new EventListener() {
                    @Override
                    public void connectFailed(Call call, InetSocketAddress inetSocketAddress, Proxy proxy, Protocol protocol, IOException ioe) {
                            //链接失败
                        NetWorkEevent netWorkEevent = new NetWorkEevent();
                        netWorkEevent.setErroMsg("网络链接失败:"+ioe.getLocalizedMessage());
                        EventBus.getDefault().postSticky(netWorkEevent);
                    }
                })
                .build();

    }

    /**
     * 单例
     * @return
     */
    public static OkhttpUtils getInstance() {
        if (null==instance)
        {
            synchronized (OkhttpUtils.class){
                if (null==instance)
                {
                    instance =  new OkhttpUtils();

                }
            }

        }
        return instance;
    }

    public void setProxy(List<NetWorkProx> prox, NetWorkProx old_id)
    {

        NetWorkProx prox1 = prox.get(new Random().nextInt(prox.size()));
        //重新构建okhttp
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10,TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .proxy(new Proxy(Proxy.Type.HTTP,new InetSocketAddress(prox1.getIp(), Integer.parseInt(prox1.getHost()))))
                .addInterceptor(new RetryAndChangeIpInterceptor(prox.get(0).getIp(),prox))
                .eventListener(new EventListener() {
                    @Override
                    public void connectFailed(Call call, InetSocketAddress inetSocketAddress, Proxy proxy, Protocol protocol, IOException ioe) {
                        //链接失败
                        NetWorkEevent netWorkEevent = new NetWorkEevent();
                        netWorkEevent.setErroMsg("网络链接失败:"+ioe.getLocalizedMessage());
                        EventBus.getDefault().postSticky(netWorkEevent);
                    }
                })
                .build();
    }



    /**
     * 鉴于该项目只有get请求,所以这里不进行过多的扩展
     * @param request
     * @param c
     */
    public void sendRequest(Request request, final Class c)
    {

        //主动取消

        call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e)
            {
                if (call.isCanceled())
                {
                    //表示网络请求被主动中断
                    EventBus.getDefault().postSticky(new NetWorkCancelEvent(c.getSimpleName()));
                    return;
                }
                praseData(null,c,false,e);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                String responsebody = response.body().string();
                praseData(responsebody,c,true,null);
            }
        });
    }

    public void callCancel()
    {
        if (call!=null)
        {
            if (call.isExecuted())
            {
                //在请求中
                call.cancel();
            }
        }
    }

    private void praseData(String s,Class c,boolean success,IOException e)
    {
       //Log.e("数据序列化结果",s);
        //直接将序列化的结果传走

        Gson gson = new Gson();
        if (e!=null)
        {
            if (c.getSimpleName().equals(YourRole.class.getSimpleName()))
            {

                BindEvent bindEvent = new BindEvent(null);
                bindEvent.setSuccess(success);
                bindEvent.setErroMsg(e.getLocalizedMessage());
                EventBus.getDefault().postSticky(bindEvent);
            }
            if (c.getSimpleName().equals(HeroGuide.class.getSimpleName()))
            {

                ListInfoEvent event = new ListInfoEvent(null);
                event.setErroMsg(e.getLocalizedMessage());
                event.setSuccess(success);
                EventBus.getDefault().postSticky(event);
            }
            if (c.getSimpleName().equals(GameInfo.class.getSimpleName()))
            {
                //战绩列表
                try {
                    GameInfoEvent event = new GameInfoEvent(null);
                    event.setSuccess(success);
                    event.setErroMsg(e.getLocalizedMessage());
                    EventBus.getDefault().postSticky(event);
                }
                catch (Exception e1)
                {
                    Log.e("错误",s);
                }

            }
        }
        else
        {
            //成功获取到数据的地方
            if (c.getSimpleName().equals(YourRole.class.getSimpleName()))
            {
                //排名(包括团分这些 都在里面)
                YourRole yourRole = gson.fromJson(s,YourRole.class);
                yourRole.setLocalruselt(s);
                BindEvent bindEvent = new BindEvent(yourRole);
                bindEvent.setSuccess(success);
                EventBus.getDefault().postSticky(bindEvent);
            }
            if (c.getSimpleName().equals(HeroGuide.class.getSimpleName()))
            {
                //战绩列表
                try {
                    HeroGuide heroGuide =gson.fromJson(s,HeroGuide.class);
                    heroGuide.setLocalruselt(s);
                    ListInfoEvent event = new ListInfoEvent(heroGuide);
                    event.setSuccess(success);
                    EventBus.getDefault().postSticky(event);
                }
                catch (Exception e1)
                {
                    Log.e("错误",s);
                }

            }
            if (c.getSimpleName().equals(GameInfo.class.getSimpleName()))
            {
                //战绩列表
                try {
                    GameInfo heroGuide =gson.fromJson(s,GameInfo.class);
                    heroGuide.setLocalruselt(s);
                    GameInfoEvent event = new GameInfoEvent(heroGuide);
                    event.setSuccess(success);
                    EventBus.getDefault().postSticky(event);
                }
                catch (Exception e1)
                {
                    Log.e("错误",s);
                }

            }
        }
    }

    /**
     * 拦截器
     */
    public class RetryAndChangeIpInterceptor implements Interceptor {
        int RetryCount = 3;
        String FirstIP;
        List<NetWorkProx> SERVERS;

        public RetryAndChangeIpInterceptor(String firsrIP, List<NetWorkProx> sERVERS) {
            FirstIP = firsrIP;
            SERVERS = sERVERS;
            RetryCount = 3;
        }

        public RetryAndChangeIpInterceptor(String firsrIP, List<NetWorkProx> sERVERS, int tryCount) {
            FirstIP = firsrIP;
            SERVERS = sERVERS;
            RetryCount = tryCount;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            // try the request
            Response response = doRequest(chain, request);
            int tryCount = 0;
            String url = request.url().toString();
            while (response == null && tryCount <= RetryCount) {
                url = switchServer(url);
                Request newRequest = request.newBuilder().url(url).build();
                Log.d("intercept", "Request is not successful 这是第"+tryCount+"次访问失败,代理地址为:"+SERVERS.get(tryCount).getIp());
                tryCount++;
                // retry the request
                response = doRequest(chain, newRequest);
            }
            if (response == null) {
                throw new IOException();
            }
            return response;
        }

        private Response doRequest(Interceptor.Chain chain, Request request) {
            Response response = null;
            try {
                response = chain.proceed(request);
            } catch (Exception e) {
            }
            return response;
        }

        private String switchServer(String url) {
            String newUrlString = url;
            if (url.contains(FirstIP)) {
                for (NetWorkProx server : SERVERS) {
                    if (!FirstIP.equals(server)) {
                        newUrlString = url.replace(FirstIP, server.getIp());
                        break;
                    }
                }
            } else {
                for (NetWorkProx server : SERVERS) {
                    if (url.contains(server.getIp())) {
                        newUrlString = url.replace(server.getIp(), FirstIP);
                        break;
                    }
                }
            }
            return newUrlString;
        }


    }

}
