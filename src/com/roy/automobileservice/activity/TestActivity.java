package com.roy.automobileservice.activity;

import android.app.Activity;
import android.content.AsyncQueryHandler;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;


import com.roy.automobileservice.R;
import com.squareup.picasso.Picasso;


import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;

import java.net.URL;




public class TestActivity extends Activity {





    public static void startAction(Context context) {
        Intent intent = new Intent(context, TestActivity.class);
        context.startActivity(intent);
    }

    private static final int CHANGE_IMAGE = 0;
    private Handler handler;
    private ImageView imageView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);
        imageView = (ImageView) findViewById(R.id.id_test);
        button = (Button) findViewById(R.id.change_bt);
        //new DownLoadImage(imageView,"http://www.chevrolet.com.cn/img/malibu-xl/jingcai/small/1.jpg").execute();
        Picasso.with(TestActivity.this).load("http://i.imgur.com/DvpvklR.png").into(imageView);

    }
    private class DownLoadImage extends AsyncTask<String,Integer,Bitmap>{
        private ImageView imageButton;
        String url;

        public DownLoadImage(ImageView imageButton,String url) {
            this.imageButton = imageButton;
            this.url = url;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            Resources res=getResources();
            Drawable bd=new BitmapDrawable(res,bitmap);

            imageButton.setImageDrawable(bd);
            System.out.println("异步加载图片完成！");
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            System.out.println("进程进度："+values);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            System.out.println("异步加载图片开始！");
            Log.d("luoxiaosong",url);
            Bitmap tmpBitmap = null;
            try {
                InputStream is = new java.net.URL(url).openStream();
                tmpBitmap = BitmapFactory.decodeStream(is);
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("KK下载图片", e.getMessage());
            }
            return tmpBitmap;

        }
    }




}
