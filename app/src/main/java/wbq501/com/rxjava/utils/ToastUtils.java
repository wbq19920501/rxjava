package wbq501.com.rxjava.utils;

import android.content.Context;
import android.widget.Toast;

import wbq501.com.rxjava.base.Config;

/**
 * Created by admin on 2018/3/6.
 */

public class ToastUtils {
    public static void ToastL(Context context,String msg){
        if (Config.isToast)
            Toast.makeText(context.getApplicationContext(),msg+"",Toast.LENGTH_LONG).show();
    }

    public static void ToastS(Context context,String msg){
        if (Config.isToast)
            Toast.makeText(context.getApplicationContext(),msg+"",Toast.LENGTH_SHORT).show();
    }

    public static void YToastL(Context context,String msg){
        Toast.makeText(context.getApplicationContext(),msg+"",Toast.LENGTH_LONG).show();
    }

    public static void YToastS(Context context,String msg){
        Toast.makeText(context.getApplicationContext(),msg+"",Toast.LENGTH_SHORT).show();
    }
}
