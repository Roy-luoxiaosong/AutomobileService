package com.roy.automobileservice.cls;

import android.widget.ImageButton;

import com.roy.automobileservice.adapter.UserNameAdapter;

/**
 * Created by Roy on 2016/6/9.
 */
public interface IOnDeleteUserButtonClickListener {
    void onClick(ImageButton button, int pos, UserNameAdapter adapter);
}
