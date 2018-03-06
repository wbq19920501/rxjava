package wbq501.com.rxjava.utils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import wbq501.com.rxjava.Interface.RetrofitService;

/**
 * Created by wbq501 on 2017/12/8.
 */

public class RetrofitDownFactory {
    public static int TIMEOUT = 1000;
    private static String BASEURL = "https://query.yahooapis.com";
    private static RetrofitDownFactory mRetrofitFactory;
    private static RetrofitService retrofitService;
    private RetrofitDownFactory(){
        OkHttpClient mOkHttpClient=new OkHttpClient.Builder()
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Response originalResponse = chain.proceed(chain.request());//对结果重新处理
                        return originalResponse
                                .newBuilder()
                                .body(new DownFileResponseBody(originalResponse))//将自定义的ResposeBody设置给它
                                .build();
                    }
                })
                .retryOnConnectionFailure(true)
                .build();
        Retrofit mRetrofit=new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())//添加gson转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(mOkHttpClient)
                .build();
        retrofitService=mRetrofit.create(RetrofitService.class);

    }

    public static RetrofitDownFactory getInstence(){
        if (mRetrofitFactory==null){
            synchronized (RetrofitDownFactory.class) {
                if (mRetrofitFactory == null)
                    mRetrofitFactory = new RetrofitDownFactory();
            }

        }
        return mRetrofitFactory;
    }
    public  RetrofitService API(){
        return retrofitService;
    }
}
