package wbq501.com.rxjava.utils;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import wbq501.com.rxjava.Interface.RequestBack;
import wbq501.com.rxjava.base.BaseJson;
import wbq501.com.rxjava.base.Config;
import wbq501.com.rxjava.base.ObserverBase;

/**
 * Created by admin on 2018/3/6.
 */

public class HttpUtils {
    private static String TAG = "wbq501.com.rxjava.utils.HttpUtils";

    public static void getLoginCode(Map<String, String> parms, final RequestBack requestBack){
        Observable<BaseJson> loginCode = RetrofitFactory.getInstence().API().getLoginCode(parms);
        POST(loginCode,requestBack);
    }

    private static void POST(Observable<BaseJson> loginCode, final RequestBack requestBack) {
        loginCode.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super BaseJson>) new ObserverBase<BaseJson>() {
            @Override
            public void onFailure(Throwable e) {
                isError(requestBack,e);
            }

            @Override
            public void onSuccess(BaseJson<BaseJson> baseJson) {
                try {
                    requestBack.success(baseJson);
                } catch (Exception e) {
                    e.printStackTrace();
                    isError(requestBack,e);
                }
            }
        });
    }

    private static void isError(RequestBack requestBack,Throwable e){
        if (Config.isError){
            requestBack.error(e.getMessage());
        }else {
            requestBack.error("数据错误");
        }
    }

    /**
     * 上传头像
     *
     * @param parms
     * @param requestBack
     */
    public static void uploadAvatar(RequestBody file, Map<String, String> parms, final RequestBack requestBack) {
        Observable<BaseJson> loginCode = RetrofitFactory.getInstence().API().uploadAvatar(file, getFileRequestparams(parms));
        POST(loginCode, requestBack);
    }

    private static Map<String, RequestBody> getFileRequestparams(Map<String, String> parms) {
        List<Map.Entry<String, String>> infoIds = new ArrayList<>(parms.entrySet());
        Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {

            public int compare(Map.Entry<String, String> lhs, Map.Entry<String, String> rhs) {
                return lhs.getKey().toString().compareTo(rhs.getKey());
            }
        });
        final TreeMap<String, RequestBody> requestParams = new TreeMap<>();
        requestParams.put("sign", RequestBody.create(MediaType.parse("text/plain"), parms.get("sign")));
        for (int i = 0; i < infoIds.size(); i++) {
            if (!infoIds.get(i).getKey().equals("sign")) {
                requestParams.put(infoIds.get(i).getKey(), RequestBody.create(MediaType.parse("text/plain"), infoIds.get(i).getValue()));
            }
        }
        return requestParams;
    }

    public static void getWether() {
        Observable<ResponseBody> loginCode = RetrofitFactory.getInstence().API().getWether();
        loginCode.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ResponseBody>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    Log.e(TAG, "onNext: " + responseBody.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onNext: " );
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
