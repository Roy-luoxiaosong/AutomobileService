package com.roy.automobileservice.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.roy.automobileservice.R;
import com.roy.automobileservice.layout.BeautyCatogoryContentFragment;

/**
 * Created by Roy on 2016/5/11.
 */
public class BeautyCatogoryContentActivity extends BaseActivity{
    public static void startAction(Context context,String name,String content){
        Intent intent = new Intent(context,BeautyCatogoryContentActivity.class);
        intent.putExtra("beauty_catogory_name",name);
        intent.putExtra("beauty_catogory_content",content);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.beauty_catogory_content);
        String name = getIntent().getStringExtra("beauty_catogory_name");
        String content = getIntent().getStringExtra("beauty_catogory_content");

        TextView textView = (TextView)findViewById(R.id.title_back_text);
        textView.setText(name);

        BeautyCatogoryContentFragment bccf = (BeautyCatogoryContentFragment)getFragmentManager().
                findFragmentById(R.id.beauty_catogory_content_view);
        bccf.refresh(content);
    }
}




