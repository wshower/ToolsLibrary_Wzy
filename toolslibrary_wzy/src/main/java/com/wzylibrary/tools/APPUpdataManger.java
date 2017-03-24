package com.wzylibrary.tools;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.Toast;

import com.wzylibrary.view.SuperCustomToast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by www on 2016/10/14.
 *  需要更新的地方调用方法 new UpdataManger(this).checkUpdateInfo(ue.getDownloadUrl(), ue.getMdesc(),Constant.appName);
 */
public class APPUpdataManger {
    // 应用程序Context
    private Context mContext;
    // 下载安装包的网络路径
    private String apkUrl = "";
    private String msg = "检测到新版本";
    private Dialog noticeDialog;// 提示有软件更新的对话框

    private String savePath = Environment.getExternalStorageDirectory().getPath();// 保存apk的文件夹
    private String saveFileName;

    // 进度条与通知UI刷新的handler和msg常量
//    private ProgressBar mProgress;
    // 进度条与通知UI刷新的handler和msg常量
    private ProgressDialog pd;
    private static final int DOWN_UPDATE = 1;
    private static final int DOWN_OVER = 2;
    private int progress;// 当前进度
    private Thread downLoadThread; // 下载线程


    private boolean interceptFlag = false;// 用户取消下载
    // 通知处理刷新界面的handler
    private Handler mHandler = new Handler() {
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWN_UPDATE:
                    pd.setProgress(progress);
                    break;

                case DOWN_OVER:
                    pd.cancel();
                    installApk();
                    break;
            }
            super.handleMessage(msg);
        }
    };


    /**
     注意此处不能传入getApplicationContext();会报错，因为只有是一个Activity才可以添加窗体
     */
    public APPUpdataManger(Context context) {
        this.mContext = context;

    }

    /**
     * 需要更新的地方调用方法 new UpdataManger(this).checkUpdateInfo(ue.getDownloadUrl(), ue.getMdesc(),Constant.appName);
     *
     * @param apkUrl  下载更新包的地址
     * @param msg  更新提示的信息
     * @param directoryName  下载的sd卡目录文件夹名
     */
    public void checkUpdateInfo(String apkUrl,String msg,String directoryName) {

        this.msg = msg;
        this.apkUrl = apkUrl;
        savePath = savePath+"/"+directoryName+"/";// 保存apk的文件夹  Environment.getExternalStorageDirectory().getPath()+"/aaa/"
        saveFileName = savePath + directoryName+".apk";


        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            showNoticeDialog();
        } else {  //如果没有sd卡
            SuperCustomToast.getInstance(mContext).showSameMsg("未检测到sd卡或空间不足");
        }
    }
    private void showNoticeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                mContext);// Builder，可以通过此builder设置改变AleartDialog的默认的主题样式及属性相关信息
        builder.setTitle("更新提示:");
        builder.setMessage(msg);
        builder.setPositiveButton("立即下载", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();// 当取消对话框后进行操作一定的代码？取消对话框
                showDownloadDialog();
            }
        });
        builder.setNegativeButton("取消", null);
        noticeDialog = builder.create();
        noticeDialog.show();
    }
    protected void showDownloadDialog() {

        pd = new ProgressDialog(mContext);
        pd.setTitle("更新中...");
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setButton(DialogInterface.BUTTON_POSITIVE, "后台更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        pd.show();

        downloadApk();
    }
    private void downloadApk() {
        if (TextUtils.isEmpty(apkUrl)) {
            Toast.makeText(mContext, "更新文件地址获取失败", Toast.LENGTH_SHORT).show();
            pd.cancel();
            return;
        } else if (!apkUrl.contains(".apk")) {
            Toast.makeText(mContext, "更新文件名错误", Toast.LENGTH_SHORT).show();
            pd.cancel();
            return;
        }
        downLoadThread = new Thread(mdownApkRunnable);
        downLoadThread.start();
    }

    private Runnable mdownApkRunnable = new Runnable() {
        @Override
        public void run() {

            URL url;
            try {
                url = new URL(apkUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();
                int length = conn.getContentLength();
                InputStream ins = conn.getInputStream();
                File file = new File(savePath);
                if (!file.exists()) {
                    file.mkdir();
                }
                String apkFile = saveFileName;
                File ApkFile = new File(apkFile);
                FileOutputStream outStream = new FileOutputStream(ApkFile);
                int count = 0;
                byte buf[] = new byte[1024];
                do {
                    int numread = ins.read(buf);
                    count += numread;
                    progress = (int) (((float) count / length) * 100);
                    // 下载进度
                    mHandler.sendEmptyMessage(DOWN_UPDATE);
                    if (numread <= 0) {
                        // 下载完成通知安装
                        mHandler.sendEmptyMessage(DOWN_OVER);
                        break;
                    }
                    outStream.write(buf, 0, numread);
                } while (!interceptFlag);// 点击取消停止下载
                outStream.close();
                ins.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };



    protected void installApk() {
        File apkfile = new File(saveFileName);
        if (!apkfile.exists()) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://" + apkfile.toString()),"application/vnd.android.package-archive");// File.toString()会返回路径信息
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);   //下载玩后自动打开安装界面
        mContext.startActivity(intent);
    }
}