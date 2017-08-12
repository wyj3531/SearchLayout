package com.foretree.app;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.foretree.app.databinding.ActivitySearchBinding;

/**
 * Created by silen on 11/08/2017.
 */

public class SearchActivity extends AppCompatActivity {
    ActivitySearchBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_search);
        setContentView(mBinding.getRoot());
    }
}
