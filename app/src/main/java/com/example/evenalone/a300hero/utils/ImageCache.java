package com.example.evenalone.a300hero.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.LruCache;

import java.io.File;

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
        String bs = Base64.encodeToString(heroname.getBytes(),Base64.DEFAULT);
        return lruCache_img.get(bs);
    }

    public Bitmap findDisk(String heroname)
    {
        String bs = Base64.encodeToString(heroname.getBytes(),Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeFile(path+"/"+bs);
        return bitmap;
    }

    public void add(Bitmap bitmap, String heroname) {
        lruCache_img.put(Base64.encodeToString(heroname.getBytes(),Base64.DEFAULT),bitmap);
    }
}
