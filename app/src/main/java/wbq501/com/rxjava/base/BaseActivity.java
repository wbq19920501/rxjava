package wbq501.com.rxjava.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import wbq501.com.rxjava.R;

/**
 * Created by admin on 2018/3/6.
 */

public abstract class BaseActivity extends AppCompatActivity{
    Unbinder bind;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        bind = ButterKnife.bind(this);
        init();
        initdata();
    }

    public void startactivity(Intent intent){
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }

    public void startactivityForResult(Intent intent,int requestCode){
        startActivityForResult(intent,requestCode);
        overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }

    public abstract int getLayoutId();
    public abstract void init();
    public abstract void initdata();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
