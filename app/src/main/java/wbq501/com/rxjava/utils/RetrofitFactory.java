package wbq501.com.rxjava.utils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import wbq501.com.rxjava.Interface.RetrofitService;

/**
 * Created by admin on 2018/3/6.
 */

public class RetrofitFactory {
    public static int TIMEOUT = 1000;
    private static String BASEURL = "https://query.yahooapis.com";


    private static RetrofitFactory mRetrofitFactory;
    private static  RetrofitService retrofitService;
    private RetrofitFactory(){
        OkHttpClient mOkHttpClient=new OkHttpClient.Builder()
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
        Retrofit mRetrofit=new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())//添加gson转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加rxjava转换器
                .client(mOkHttpClient)
                .build();
        retrofitService=mRetrofit.create(RetrofitService.class);

    }

    public static RetrofitFactory getInstence(){
        if (mRetrofitFactory==null){
            synchronized (RetrofitFactory.class) {
                if (mRetrofitFactory == null)
                    mRetrofitFactory = new RetrofitFactory();
            }

        }
        return mRetrofitFactory;
    }
    public  RetrofitService API(){
        return retrofitService;
    }
}
