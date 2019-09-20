package com.example.evenalone.a300hero.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ImageCenter
{
    private ImageDownLoader downLoader;
    private ImageCache imageCache;
    private static volatile ImageCenter instance;
    private Context context;

    private ImageCenter(Context context)
    {
        this.context = context;
        imageCache = new ImageCache(context);
        downLoader = new ImageDownLoader(context,imageCache);


    }

    public static ImageCenter getInstance(Context context) {
       if (instance==null)
       {
           synchronized (ImageCenter.class)
           {
               if (instance==null)
               {
                   instance = new ImageCenter(context);
               }
           }
       }
       return instance;
    }
    //传入视图
    public void setPic(ImageView imageView,String heroname,String imgurl)
    {
        //别忘记给imgaeview设置tag
        imageView.setTag(heroname);
        Bitmap bitmap;
        //获取内存是否存有该bitmap
        bitmap = imageCache.findMemory(heroname);
        if (bitmap!=null)
        {
           imageView.setImageBitmap(bitmap);
           return;
        }

        bitmap = imageCache.findDisk(heroname,imageView,imgurl);
        if (bitmap!=null)
        {
            imageView.setImageBitmap(bitmap);
            return;
        }
        //传入去异步加载
        downLoader.downloadAndSet(imageView,heroname,imgurl);

    }

    public void addMermory(String name, Bitmap result_b) {
        imageCache.add(result_b,name);
    }

    public Bitmap getCache(String heroname) {
        //本地获取
        Bitmap bitmap;
        bitmap = imageCache.findDiskbyTools(heroname);
        if (bitmap!=null)
        {
            return bitmap;
        }
        return null;
    }

    public void donwloadOnly(String iconfile,String name) {
        downLoader.downloadonely(iconfile,name);
    }
}
