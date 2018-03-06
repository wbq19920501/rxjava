package wbq501.com.rxjava.Interface;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import wbq501.com.rxjava.base.BaseJson;

/**
 * Created by admin on 2018/3/6.
 */

public interface RetrofitService {

    /**
     * 获取短信验证码
     *
     * @param map
     * @return
     */
    @POST("index.php?s=/api/Member/login")
    @FormUrlEncoded
    Observable<BaseJson> getLoginCode(@FieldMap Map<String, String> map);

    /**
     * 上传头像
     *
     * @param map
     * @return
     */
    @POST("index.php?s=/api/Member/uploadAvatar")
    @Multipart
    Observable<BaseJson> uploadAvatar(@Part("fields\";filename=\"avatar.png\"") RequestBody file, @PartMap Map<String, RequestBody> map);

    @Streaming
    @GET
    Observable<ResponseBody> download(@Header("RANGE") String downParam, @Url String url);

    @Streaming
    @GET
    Observable<ResponseBody> downloadapk(@Url String url);

    @GET("/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D\"北京\")&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys")
    Observable<ResponseBody> getWether();
}
