package com.john.todo;

import androidx.fragment.app.Fragment;

public class TabModel {
    String title;
    Fragment fragment;

    TabModel(String title, Fragment f){
        this.title = title;
        this.fragment = f;
    }
}
