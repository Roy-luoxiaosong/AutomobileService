package com.roy.automobileservice.layout;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.roy.automobileservice.R;

/**
 * Created by Roy on 2016/5/11.
 */
public class BeautyCatogoryContentFragment extends Fragment {
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.beauty_catogory_content_frg,container,false);
        return view;
    }
    public void refresh(String beautyCatogoryContent){
        TextView content = (TextView) view.findViewById(R.id.beauty_catogory_content);
        content.setText(beautyCatogoryContent);
    }
}
