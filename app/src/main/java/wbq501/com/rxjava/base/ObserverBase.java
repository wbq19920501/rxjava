package wbq501.com.rxjava.base;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by admin on 2018/3/6.
 */

public abstract class ObserverBase<T> implements Observer<BaseJson<T>>{
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(BaseJson<T> baseJson) {
        onSuccess(baseJson);
    }

    @Override
    public void onError(Throwable e) {
        onFailure(e);
    }

    @Override
    public void onComplete() {

    }

    public abstract void onFailure(Throwable e);
    public abstract void onSuccess(BaseJson<T> baseJson);
}
