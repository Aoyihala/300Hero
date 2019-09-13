package com.example.evenalone.a300hero.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.downloader.Error;
import com.downloader.OnDownloadListener;
import com.downloader.PRDownloader;

import java.io.File;

public class ImageDownLoader
{
    private ImageCache imageCache;
    public ImageDownLoader(Context context, ImageCache imageCache) {
        this.imageCache = imageCache;

    }

    public void downloadAndSet(final ImageView imageView, final String heroname, final String imgurl) {
        File file = new File(imageCache.path,heroname);
        if (file.exists())
        {
            Log.e("缓存文件已存在",heroname);
            Bitmap bitmap = BitmapFactory.decodeFile(imageCache.path+"/"+heroname);
            if (bitmap!=null)
            {
                //加入内存
                imageCache.add(bitmap,heroname);
                //判断tag
                if (imageView.getTag().equals(heroname))
                {
                    imageView.setImageBitmap(bitmap);
                }
            }
            return;
        }
        PRDownloader.download(imgurl,imageCache.path,heroname)
        .build()
        .start(new OnDownloadListener() {
            @Override
            public void onDownloadComplete() {
                //到这里diskCache已经完成
                Bitmap bitmap = BitmapFactory.decodeFile(imageCache.path+"/"+heroname);
                if (bitmap!=null)
                {
                    //加入内存
                    imageCache.add(bitmap,heroname);
                    //判断tag
                    if (imageView.getTag().equals(heroname))
                    {
                        imageView.setImageBitmap(bitmap);
                    }
                }
            }

            @Override
            public void onError(Error error) {
                //这里出现错误一般是RenameFile fail
                //防止正在操作的重名文件被覆盖
                //出错
                imageView.setTag(null);
                Glide.with(imageView.getContext()).load(imgurl).into(imageView);
                if ((error.getConnectionException().getLocalizedMessage()+" ").contains("Rename"))
                {
                    //删除
                    File file = new File(imageCache.path,heroname);
                    //重新调用该方法
                    if (file.delete())
                    {
                        downloadAndSet(imageView,heroname,imgurl);
                    }
                }
            }
        });

    }

    public void downloadonely(final String heroname) {

        final String bs = Base64.encodeToString(heroname.getBytes(),Base64.DEFAULT);
        PRDownloader.download(Contacts.IMG+heroname,imageCache.path,bs)
                .build()
                .start(new OnDownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        //到这里diskCache已经完成
                        Bitmap bitmap = BitmapFactory.decodeFile(imageCache.path+"/"+bs);
                        if (bitmap!=null)
                        {
                            //加入内存
                            imageCache.add(bitmap,heroname);
                        }
                    }

                    @Override
                    public void onError(Error error) {

                    }
                });
    }
}
