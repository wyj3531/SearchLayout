package com.foretree.app;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.foretree.app.databinding.ActivitySearchBinding;
import com.foretree.search.LayoutType;
import com.foretree.search.SearchLayoutFactory;

/**
 * Created by silen on 11/08/2017.
 */

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySearchBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        setContentView(binding.getRoot());
        binding.contentPanel.addView(SearchLayoutFactory.createSearchFractory(this, LayoutType.FLOW));
    }
}
