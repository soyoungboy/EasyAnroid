package com.soyoungboy.base.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

/**
 * TODO: intent工具类
 *
 * @author wang.fb
 */
public class IntentUtil {

    public static final int INTENT_REQUEST_CODE_ALBUM = 1001;
    public static final int INTENT_REQUEST_CODE_CAMERA = 1002;
    public static final int INTENT_REQUEST_CODE_CROP = 1003;

    public static final String[] HTML = new String[] { ".htm", ".html", ".php", ".jsp" };

    public static final String[] IMAGE = new String[] { ".png", ".gif", ".jpg", ".jpeg", ".bmp" };

    public static final String[] AUDIO = new String[] { ".mp3", ".wav", ".ogg", ".midi" };

    public static final String[] VIDEO = new String[] { ".mp4", ".rmvb", ".avi", ".flv" };

    public static final String[] APK = new String[] { ".apk" };

    public static final String[] TEXT = new String[] { ".txt", ".java", ".c", ".cpp", ".py", ".xml",
        ".json", ".log" };

    public static final String[] CHM = new String[] { ".chm" };

    public static final String[] WORD = new String[] { ".doc", ".docx" };

    public static final String[] EXCEL = new String[] { ".xls", ".xlsx" };

    public static final String[] PPT = new String[] { ".ppt", ".pptx" };

    public static final String[] PDF = new String[] { ".pdf" };


    /**
     * 跳转到相册
     */
    public static boolean intentToAlbum(Activity activity) {
        Intent it = new Intent(Intent.ACTION_PICK, null);
        it.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        activity.startActivityForResult(it, INTENT_REQUEST_CODE_ALBUM);
        return true;
    }

    /**
     * 跳转到相�?
     *
     * @param activity
     * @param savePath
     * @param saveName
     * @return
     */
    //	public static boolean intentToCamera(Activity activity, Context context,
    //			String savePath, String saveName) {
    //		if (StringUtil.isEmpty(savePath) || StringUtil.isEmpty(saveName)) {
    //			return false;
    //		}
    //		File dirFile = FileUtil.createDirFile(context, savePath);
    //		if (dirFile == null) {
    //			return false;
    //		}
    //		File file = FileUtil.createNewFile(context, savePath + saveName);
    //		if (file == null) {
    //			return false;
    //		}
    //		Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    //		it.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
    //		activity.startActivityForResult(it, INTENT_REQUEST_CODE_CAMERA);
    //		return true;
    //	}


    /**
     * 跳转到浏览器
     *
     * @param uri <li>http://www.google.com</li> <li>https://www.google.com</li>
     */
    public static boolean intentToBrowser(Context context, String uri) {
        return intentToView(context, uri);
    }


    /**
     * 跳转到地图
     *
     * @param uri <li>geo:latitude,longitude</li> <br>
     * <li>geo:latitude,longitude?z=zoom</li><br>
     * <li>geo:0,0?q=my+street+address</li><br>
     * <li>geo:0,0?q=business+near+city</li><br>
     * <li>
     * google.streetview:cbll=lat,lng&cbp=1,yaw,,pitch,zoom&mz=
     * mapZoom</li><br>
     */
    public static boolean intentToMap(Context context, String uri) {
        return intentToView(context, uri);
    }


    /**
     * 跳转到View组件
     */
    public static boolean intentToView(Context context, String uri) {
        if (context == null || StringUtil.isEmpty(uri)) {
            return false;
        }
        try {
            Uri u = Uri.parse(uri);
            Intent it = new Intent(Intent.ACTION_VIEW, u);
            context.startActivity(it);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(context, "抱歉，设备没有可使用的地图软件", Toast.LENGTH_LONG).show();
        }
        return true;
    }


    /**
     * 跳转到打电话界面
     */
    public static boolean intentToDIAL(Context context, String phoneNumber) {
        if (context == null || StringUtil.isEmpty(phoneNumber)) {
            return false;
        }
        Uri u = Uri.parse("tel:" + phoneNumber);
        Intent it = new Intent(Intent.ACTION_DIAL, u);
        context.startActivity(it);
        return true;
    }


    /**
     * 直接拨打电话
     */
    public static boolean intentToCall(Context context, String phoneNumber) {
        if (context == null || StringUtil.isEmpty(phoneNumber)) {
            return false;
        }
        Uri u = Uri.parse("tel:" + phoneNumber);
        Intent it = new Intent(Intent.ACTION_CALL, u);
        context.startActivity(it);
        return true;
    }


    /**
     * 调用发短信界面
     */
    public static boolean intentToSMS(Context context, String phoneNumber, String smsBody) {
        if (context == null || StringUtil.isEmpty(phoneNumber)) {
            return false;
        }
        Uri uri = Uri.parse("smsto:" + phoneNumber);
        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
        if (!StringUtil.isEmpty(smsBody)) {
            it.putExtra("sms_body", smsBody);
        }
        context.startActivity(it);
        return true;
    }


    /**
     * 调用发邮件界面
     */
    public static boolean intentToEmail(Context context, String emailAddress, String emailBody) {
        if (context == null || StringUtil.isEmpty(emailAddress)) {
            return false;
        }
        Intent it = new Intent(android.content.Intent.ACTION_SEND);
        it.setType("plain/text");
        it.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { emailAddress });
        it.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
        if (StringUtil.isEmpty(emailBody)) {
            it.putExtra(android.content.Intent.EXTRA_TEXT, "");
        } else {
            it.putExtra(android.content.Intent.EXTRA_TEXT, emailBody);
        }
        context.startActivity(Intent.createChooser(it, "请选择邮件发送软件"));
        return true;
    }


    /**
     * 查询应用在应用市场的内容
     */
    public static boolean intentToSearchMarket(Context context, String packageName) {
        if (context == null || StringUtil.isEmpty(packageName)) {
            return false;
        }
        String str = "market://search?q=pname:" + packageName;
        try {
            Intent it = new Intent(Intent.ACTION_VIEW);
            it.setData(Uri.parse(str));
            context.startActivity(it);
            return true;
        } catch (Exception e) {

        }
        return false;
    }


    /**
     * 打开应用在应用市场的内容
     */
    public static boolean intentToDetailMarket(Context context, String packageName) {
        if (context == null || StringUtil.isEmpty(packageName)) {
            return false;
        }
        String str = "market://details?id=" + packageName;
        try {
            Intent it = new Intent(Intent.ACTION_VIEW);
            it.setData(Uri.parse(str));
            context.startActivity(it);
            return true;
        } catch (Exception e) {

        }
        return false;
    }


    /**
     * 打开应用程序
     *
     * @param packageName 包名
     */
    public static boolean intentToOpenApplication(Context context, String packageName, String className) {
        if (context == null || StringUtil.isEmpty(packageName) || StringUtil.isEmpty(className)) {
            return false;
        }
        Intent it = new Intent(Intent.ACTION_MAIN, null);
        it.addCategory(Intent.CATEGORY_LAUNCHER);
        ComponentName cn = new ComponentName(packageName, className);
        it.setComponent(cn);
        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(it);
        return true;
    }


    /**
     * 跳转到桌面
     */
    public static boolean intentToHome(Context context) {
        if (context == null) {
            return false;
        }
        Intent it = new Intent(Intent.ACTION_MAIN);
        it.addCategory(Intent.CATEGORY_HOME);
        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(it);
        return true;
    }

}
