package com.example.evenalone.a300hero.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.util.LruCache;

import java.io.File;
import java.io.FileNotFoundException;

public class ImageCache
{
    public String path;
    private long maxmemory;
    private int cachesize;
    private Runtime rt;
    //使用lrucache来实现全自动的栈管理
    private LruCache<String,Bitmap> lruCache_img;
    public ImageCache(Context context) {
        rt = Runtime.getRuntime();
        maxmemory = rt.totalMemory()/1024;
        cachesize = (int) (maxmemory/10);
        lruCache_img = new LruCache<String,Bitmap>(cachesize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes()*value.getHeight()/1024;
            }
        };
        path = context.getFilesDir().getAbsolutePath()+"/imgfile";
    }

    public Bitmap findMemory(String heroname) {
        //转换为base64

        return lruCache_img.get(heroname);
    }

    public Bitmap findDisk(String heroname)  {

            Bitmap bitmap = BitmapFactory.decodeFile(path+"/"+heroname);
            if (bitmap==null)
            {
                Log.e("英雄头像:"+heroname,"重新下载");
                //在扔之前先下载
                return null;
            }
            return bitmap;
    }

    public void add(Bitmap bitmap, String heroname) {
        if (lruCache_img.get(heroname)!=null)
        {
            //相同英雄头像就不用下了
            return;
        }
        lruCache_img.put(heroname,bitmap);
    }
}
