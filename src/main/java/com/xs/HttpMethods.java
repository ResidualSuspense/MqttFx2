package com.xs;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by xiaosong on 2017/1/22.
 */
public class HttpMethods {

    public static final String BASE_URL = "http://www.iconfont.cn/";

    private static final int DEFAULT_TIMEOUT = 5;

    private Retrofit retrofit;
    private ServiceApi ServiceApi;

    //构造方法私有
    private HttpMethods() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        ServiceApi = retrofit.create(ServiceApi.class);
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder{
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    //获取单例
    public static HttpMethods getInstance(){
        return SingletonHolder.INSTANCE;
    }

    public ServiceApi getServiceApi() {
        return ServiceApi;
    }
}
