package com.edfora.sunny.edfora.utils;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

/**
 * Created by Sunny on 19-03-2017.
 */

public class ImageUtils {


    public static void loadImage(Context context, ImageView imageView, String url)
    {
        Picasso.Builder builder = new Picasso.Builder(context);
        builder.listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
            }
        });
        builder.downloader(new OkHttpDownloader(context));
        builder.build().load(url).into(imageView);
    }
}
