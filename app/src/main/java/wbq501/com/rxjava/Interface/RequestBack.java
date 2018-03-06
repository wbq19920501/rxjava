package wbq501.com.rxjava.Interface;

import wbq501.com.rxjava.base.BaseJson;

/**
 * Created by admin on 2018/3/6.
 */

public interface RequestBack<T> {
    void success(BaseJson<T> msg) throws Exception;
    void error(String errormsg);
}
