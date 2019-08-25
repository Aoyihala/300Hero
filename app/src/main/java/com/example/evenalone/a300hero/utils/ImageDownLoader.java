package com.example.evenalone.a300hero.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.ImageView;

import com.downloader.Error;
import com.downloader.OnDownloadListener;
import com.downloader.PRDownloader;

import java.io.File;

public class ImageDownLoader
{
    private ImageCache imageCache;
    public ImageDownLoader(Context context) {
        imageCache = new ImageCache(context);

    }

    public void downloadAndSet(final ImageView imageView, final String heroname, final String imgurl) {
        final String bs = Base64.encodeToString(heroname.getBytes(),Base64.DEFAULT);
        PRDownloader.download(imgurl,imageCache.path,bs)
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
                if ((error.getConnectionException().getLocalizedMessage()+" ").contains("Rename"))
                {
                    //删除
                    File file = new File(imageCache.path,Base64.encodeToString(heroname.getBytes(),Base64.DEFAULT));
                    //重新调用该方法
                    if (file.delete())
                    {
                        downloadAndSet(imageView,heroname,imgurl);
                    }
                }
            }
        });

    }
}
