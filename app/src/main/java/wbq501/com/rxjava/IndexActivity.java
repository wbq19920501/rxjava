package wbq501.com.rxjava;

import android.Manifest;

import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;
import wbq501.com.rxjava.base.BaseActivity;
import wbq501.com.rxjava.utils.ToastUtils;

/**
 * Created by admin on 2018/3/6.
 */

public class IndexActivity extends BaseActivity{
    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {
        requestPermissions();
    }

    private void requestPermissions() {
        new RxPermissions(IndexActivity.this).request(Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.READ_LOGS,
                Manifest.permission.SET_DEBUG_APP,
                Manifest.permission.SYSTEM_ALERT_WINDOW,
                Manifest.permission.GET_ACCOUNTS,
                Manifest.permission.WRITE_APN_SETTINGS,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean)
                            ToastUtils.ToastL(IndexActivity.this,"权限已开启");
                        else
                            ToastUtils.ToastL(IndexActivity.this,"部分权限被拒绝");
                    }
                });
    }

    @Override
    public void initdata() {

    }
}
