package wbq501.com.rxjava

import android.Manifest
import android.app.Activity
import android.content.Intent
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.functions.Consumer
import okhttp3.MediaType
import okhttp3.RequestBody
import wbq501.com.rxjava.Interface.RequestBack
import wbq501.com.rxjava.base.BaseActivity
import wbq501.com.rxjava.base.BaseJson
import wbq501.com.rxjava.utils.HttpUtils
import wbq501.com.rxjava.utils.ToastUtils
import java.io.File

/**
 * Created by admin on 2018/3/6.
 */

class UpImage : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun init() {
        RxPermissions(this).request( Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.CAMERA).subscribe{aBoolean ->
            if (aBoolean!!)
                goTakePhoto()
            else
                ToastUtils.YToastL(UpImage@this,"部分权限被拒绝")
        }
    }

    private fun goTakePhoto() {
        PictureSelector.create(UpImage@this).openGallery(PictureMimeType.ofImage())
                .imageSpanCount(3).previewImage(true).enableCrop(true).isCamera(false)
                .freeStyleCropEnabled(true).circleDimmedLayer(true).maxSelectNum(1).showCropGrid(false)
                .forResult(PictureConfig.CHOOSE_REQUEST)
    }

    override fun initdata() {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            when(requestCode){
                PictureConfig.CHOOSE_REQUEST ->{
                    val selectList = PictureSelector.obtainMultipleResult(data)
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    upheadimg(selectList[0].cutPath)
                }
            }
        }
    }

    private fun upheadimg(cutPath: String?) {
        var parms = HashMap<String,String>()
        parms.put("token","token")
        val requestBody = RequestBody.create(MediaType.parse("image/*"), File(cutPath))
        HttpUtils.uploadAvatar(requestBody,parms,object : RequestBack<BaseJson<String>>{
            override fun success(msg: BaseJson<BaseJson<String>>?) {

            }
            override fun error(errormsg: String?) {

            }

        })
    }
}
