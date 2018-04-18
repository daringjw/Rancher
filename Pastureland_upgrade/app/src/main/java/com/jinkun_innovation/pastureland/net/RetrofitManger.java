package com.jinkun_innovation.pastureland.net;

import android.os.Environment;
import android.util.Log;

import com.jinkun_innovation.pastureland.utilcode.SpUtil;
import com.jinkun_innovation.pastureland.utilcode.util.LogUtils;
import com.jinkun_innovation.pastureland.utilcode.util.NetworkUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by YX on 16/7/14.
 */
public class RetrofitManger {


    private String BASE_URL = "http://" + SpUtil.getIP() + ":" + SpUtil.getPort();
    private Retrofit mRetrofit;


    private static OkHttpClient mOkHttpClient;

    private static RetrofitManger mRetrofitManger;
//    //短缓存有效期为1秒钟
//    public static final int CACHE_STALE_SHORT = 10;
//    //长缓存有效期为7天
//    public static final int CACHE_STALE_LONG = 120;

    public static final int maxAge = 60 * 60; // 有网络时 设置缓存超时时间1个小时
    public static final int maxStale = 60 * 60 * 24 * 28; // 无网络时，设置超时为4周


    public static RetrofitManger getInstance() {
        if (mRetrofitManger == null) {
            mRetrofitManger = new RetrofitManger();
        }
        mRetrofitManger = new RetrofitManger();
        return mRetrofitManger;
    }

    public RetrofitManger() {
        initOkHttpClient();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(mOkHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//Rxjava
                .addConverterFactory(GsonConverterFactory.create())//gson解析
                .build();
    }

    private void initOkHttpClient() {
        LoggingInterceptor loggingInterceptor = new LoggingInterceptor();
        File cacheFile = new File(Environment.getExternalStorageDirectory(), "HttpCache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb
        if (mOkHttpClient == null) {
            synchronized (RetrofitManger.class) {
                if (mOkHttpClient == null) {
                    mOkHttpClient = new OkHttpClient.Builder()
                            .addInterceptor(loggingInterceptor)
                            .retryOnConnectionFailure(true)
                            .connectTimeout(30, TimeUnit.SECONDS)
                            .readTimeout(30, TimeUnit.SECONDS)
                            .writeTimeout(30, TimeUnit.SECONDS)
                            .build();
                }
            }
        }
    }


    // 云端响应头拦截器，用来配置缓存策略
    private static final Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetworkUtils.isAvailableByPing()) {
                //无网络读缓存
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
                Log.e("TAG", "no network");
            }
            Response originalResponse = chain.proceed(request);
            if (NetworkUtils.isAvailableByPing()) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                //设置缓存时间为60秒，并移除了pragma消息头，移除它的原因是因为pragma也是控制缓存的一个消息头属性
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder().header("Cache-Control", "public, max-age=" + maxAge).removeHeader("Pragma").build();
            } else {
                return originalResponse.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale).removeHeader("Pragma").build();
            }
        }
    };

    private static class LoggingInterceptor implements Interceptor {
        private static final Charset UTF8 = Charset.forName("UTF-8");

        @Override
        public Response intercept(Chain chain) throws IOException {

            long t1 = System.nanoTime();
            Request request = chain.request();

            LogUtils.e(String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));

            Response response = chain.proceed(request);

            long t2 = System.nanoTime();

            LogUtils.e(String.format("Received response for %s in %.1fms%n%s",
                    request.url(), (t2 - t1) / 1e6d, response.headers(), response.body().toString()));

            BufferedSource source = response.body().source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();
            String logStr = "\n--------------------"
                    .concat("  begin--------------------\n")
                    .concat("\nnetwork code->").concat(response.code() + "")
                    .concat("\nurl->").concat(request.url() + "")
                    .concat("\nrequest headers->").concat(request.headers() + "")
                    .concat("\nbody->").concat(buffer.clone().readString(UTF8));
            LogUtils.e(logStr);


            return response;
        }
    }


    public <T> T createReq(Class<T> reqServer) {
        return mRetrofit.create(reqServer);
    }

}