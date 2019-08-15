package com.example.evenalone.a300hero.service;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * JobScheduler管理类，单例模式
 * 执行系统任务
 */

public class JobSchedulerManager {

    private static final int JOB_ID = 121;
    private static JobSchedulerManager mJobManager;
    private JobScheduler mJobScheduler;
    private static Context mContext;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private JobSchedulerManager(Context ctxt) {
        this.mContext = ctxt;
        mJobScheduler = (JobScheduler) ctxt.getSystemService(Context.JOB_SCHEDULER_SERVICE);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public final static JobSchedulerManager getJobSchedulerInstance(Context ctxt) {
        if (mJobManager == null) {
            mJobManager = new JobSchedulerManager(ctxt);
        }
        return mJobManager;
    }

    @TargetApi(21)
    public void startJobScheduler() {
        // 如果JobService已经启动或API<21，返回
        if (JobTimer.isJobServiceAlive() || isBelowLOLLIPOP()) {
            return;
        }
        // 构建JobInfo对象，传递给JobSchedulerService
        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, new ComponentName(mContext, JobTimer.class));
        //设置每3秒执行一下任务
        if (Build.VERSION.SDK_INT<=24)
        {
            builder.setPeriodic(3000);
        }

        // 设置设备重启时，执行该任务
        builder.setPersisted(true);
     /*   builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);*/
        // 当插入充电器，执行该任务
        builder.setRequiresCharging(true);
        if (Build.VERSION.SDK_INT>=24)
        {
            builder.setOverrideDeadline(3000);
            //设置最小间隔
            builder.setMinimumLatency(3000);
        }

        JobInfo info = builder.build();
        //开始定时执行该系统任务
        mJobScheduler.schedule(info);
    }

    @TargetApi(21)
    public void stopJobScheduler() {
        if (isBelowLOLLIPOP())
            return;
        mJobScheduler.cancelAll();
    }

    private boolean isBelowLOLLIPOP() {
        // API< 21
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP;
    }
}
