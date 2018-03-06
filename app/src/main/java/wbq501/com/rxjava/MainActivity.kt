package wbq501.com.rxjava

import android.os.Environment
import android.os.Handler
import android.os.Message
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import wbq501.com.rxjava.base.BaseActivity
import wbq501.com.rxjava.bean.DownloadBean
import wbq501.com.rxjava.utils.RxBus
import wbq501.com.rxjava.utils.UpdateManager

class MainActivity : BaseActivity() {

    var cd = CompositeDisposable()
    var downurl: String = ""
    //需要判断存储地址是否可用
    val apk_file = Environment.getExternalStorageDirectory().absolutePath + "/rx/rx.apk"

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun init() {

    }

    override fun initdata() {
        //取消下载
//        UpdateManager.unSubscribe(cd)
        subscribeEvent()
        UpdateManager.downloadApk(MainActivity@this,downurl,apk_file,cd)
    }

    fun subscribeEvent(){
        RxBus.getDefault().toObservable(DownloadBean::class.java).subscribe(object : Observer<DownloadBean>{
            override fun onError(e: Throwable?) {
                subscribeEvent()
            }

            override fun onNext(downloadBean : DownloadBean?) {
                var progress : Int = Math.round(downloadBean!!.bytesReaded / downloadBean!!.total.toDouble() * 100).toInt()
                var message = Message()
                message.what = progress
                message.arg1 = downloadBean.bytesReaded.toInt()
                message.arg2 = downloadBean.total.toInt()
                handler.sendMessage(message)
            }

            override fun onComplete() {
                subscribeEvent()
            }

            override fun onSubscribe(d: Disposable?) {
                cd.add(d)
            }
        })
    }

    val handler = object : Handler(){
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            var arg1:Int = msg!!.arg1  //当前进度
            var arg2:Int = msg!!.arg2  //总共进度
            var aa:Int = msg!!.what  //百分比
        }
    }
}
