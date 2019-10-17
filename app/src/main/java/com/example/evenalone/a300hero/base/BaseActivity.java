package com.example.evenalone.a300hero.base;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.evenalone.a300hero.bean.UpdateBean;
import com.example.evenalone.a300hero.event.BaseEvent;
import com.example.evenalone.a300hero.event.UpdateVersionEvent;
import com.zhy.base.fileprovider.FileProvider7;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

import butterknife.ButterKnife;


public abstract class BaseActivity extends LibaryActivity
{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initdata();
        initview();
        if (!EventBus.getDefault().isRegistered(this))
        {
            EventBus.getDefault().register(this);
        }


    }

    //接受事件
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateVersion(UpdateVersionEvent eva)
    {

        final UpdateBean updateBean = eva.getUpdateBean();
        if (updateBean!=null)
        {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("版本更新"+(updateBean.getIscoerce()==true?"(强制更新)":"(可取消)"));
            alert.setMessage("版本"+updateBean.getVersion()+"\n"+"时间:"+updateBean.getTime()+"\n"+updateBean.getDes());
            if (updateBean.getIscoerce())
            {
                alert.setCancelable(false);
            }
            else
            {
                alert.setCancelable(true);
            }
            alert.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    updateApp(updateBean.getUrl());
                }
            });
            alert.setNegativeButton((updateBean.getIscoerce() == true ? "退出app" : "取消"), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (updateBean.getIscoerce())
                    {
                        System.exit(0);
                    }
                    else
                    {
                        dialog.dismiss();
                    }
                }
            });
            alert.create();
            alert.show();

        }
    }

    private void updateApp(String url) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("更新中");
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        progressDialog.show();
        x.http().get(new RequestParams(url), new Callback.ProgressCallback<File>() {
            @Override
            public void onSuccess(File result) {
                if (result!=null)
                {
                    progressDialog.dismiss();
                    installApk(result);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {

            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                int percent = (int) ((((double)current)/((double)total))*100);
                progressDialog.setProgress(percent);
            }
        });


    }

    private void installApk(File result) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //APKfile为你的安装包文件url
        FileProvider7.setIntentDataAndType(this,intent, "application/vnd.android.package-archive",result, true);
        this.startActivity(intent);
    }


    protected abstract void initview();

    protected abstract void initdata();

    protected abstract int getLayoutId();

    protected void setBackBtn(View view)
    {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
