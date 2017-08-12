package com.foretree.search;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.foretree.search.databinding.LayoutSearchBinding;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;


/**
 * Created by silen on 11/08/2017.
 */

public class SearchLayout extends LinearLayout implements View.OnFocusChangeListener, SearchView.OnQueryTextListener {
    LayoutSearchBinding mBinding;
    private SearchModel model;
    private LayoutInflater inflater;


    public class Presenter {
        public void cancelSearch(View view) {

        }

        public void clearNames(View view) {
            boolean b = CardSearchManger.getInstance(getContext()).clearAllNames();
            if (b) initHistoryData();
        }
    }

    public SearchLayout(Context context) {
        this(context, null);
    }

    public SearchLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUp(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SearchLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setUp(context, attrs, defStyleAttr);
    }

    private void setUp(Context context, AttributeSet attrs, int defStyleAttr) {
        inflater = LayoutInflater.from(context);
        mBinding = DataBindingUtil.inflate(inflater, R.layout.layout_search, this, true);
        mBinding.setPresenter(new Presenter());
        model = new SearchModel();
        mBinding.setModel(model);
        initAttr(context, attrs, defStyleAttr);
        mBinding.svSearch.setOnQueryTextListener(this);
        mBinding.svSearch.setOnQueryTextFocusChangeListener(this);
        initHistoryData();
        initNetTags();
        initSuggestion();
    }

    private void initSuggestion() {

    }

    private void initNetTags() {
        TagFlowLayout flowLayoutRecommend = mBinding.flowLayoutRecommend;
        List<String> allNames = CardSearchManger.getInstance(getContext()).getAllNames();
        TagAdapter adapter = new TagAdapter<String>(allNames) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                View view = inflater.inflate(R.layout.item_search_tag, null);
                CheckedTextView tag = (CheckedTextView) view.findViewById(R.id.tv_tag);
                if (position == 2) tag.setChecked(true);
                tag.setText(s);
                return tag;
            }
        };
        flowLayoutRecommend.setAdapter(adapter);
        flowLayoutRecommend.setOnTagClickListener((view, position, parent) -> {
            String item = (String) adapter.getItem(position);
            return false;
        });
    }

    private void initHistoryData() {
        List<String> allNames = CardSearchManger.getInstance(getContext()).getAllNames();
        int padding = (int) (dip2px(10f) * getBaseScale());
        TagAdapter adapter = new TagAdapter<String>(allNames) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView textView = new TextView(getContext());
                textView.setLayoutParams(new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                textView.setWidth((int) (dip2px(80f) * getBaseScale()));
                textView.setLines(1);
                textView.setGravity(Gravity.CENTER);
                textView.setEllipsize(TextUtils.TruncateAt.END);
                textView.setPadding(padding, padding, padding, padding);
                textView.setText(s);
                return textView;
            }
        };
        mBinding.flowLayoutHistory.setAdapter(adapter);
        mBinding.flowLayoutHistory.setOnTagClickListener((view, position, parent) -> {
            String item = (String) adapter.getItem(position);
            return false;
        });
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        if (!query.isEmpty())
            CardSearchManger.getInstance(getContext()).putName(query);
        initHistoryData();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        model.setName(newText);
        return true;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

    }

    private void initAttr(Context context, AttributeSet attrs, int defStyleAttr) {

    }

    public SearchView getSearchView() {
        return mBinding.svSearch;
    }

    /* 根据手机的分辨率从 dp 的单位 转成为 px(像素) */
    public int dip2px(float dpValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public float getBaseScale() {
        return getContext().getResources().getDisplayMetrics().widthPixels / (getContext().getResources().getDisplayMetrics().density * 360);
    }
}
