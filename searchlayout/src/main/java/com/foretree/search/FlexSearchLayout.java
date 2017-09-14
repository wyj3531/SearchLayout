package com.foretree.search;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.SearchView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.foretree.search.adapter.AbstractRecyclerAdapter;
import com.foretree.search.adapter.TagBindingAdapter;
import com.foretree.search.databinding.LayoutFlexSearch1Binding;
import com.google.android.flexbox.AlignContent;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.lang.reflect.Field;
import java.util.List;


/**
 * Created by silen on 11/08/2017.
 */

public class FlexSearchLayout extends LinearLayout
        implements View.OnFocusChangeListener, SearchView.OnQueryTextListener, TagBindingAdapter.OnItemClickListener {
    private FlexboxLayoutManager flexboxLayoutManager;
    private LayoutFlexSearch1Binding mBinding;
    private List<SearchModel> allNames;

    public class Presenter {
        public void cancelSearch(View view) {

        }

        public void clearNames(View view) {
            boolean b = SearchManger.getInstance(getContext()).clearAllNames();
            if (b) initHistoryData(allNames);
        }
    }

    public FlexSearchLayout(Context context) {
        this(context, null);
    }

    public FlexSearchLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlexSearchLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUp(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FlexSearchLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setUp(context, attrs, defStyleAttr);
    }

    private void setUp(Context context, AttributeSet attrs, int defStyleAttr) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_flex_search_1, this, true);
        mBinding.setPresenter(new Presenter());
        initAttr(context, attrs, defStyleAttr);
        allNames = SearchManger.getInstance(getContext()).getAllSearchModels();
        mBinding.svSearch.setOnQueryTextListener(this);
        mBinding.svSearch.setOnQueryTextFocusChangeListener(this);
        flexboxLayoutManager = new FlexboxLayoutManager(getContext());
        flexboxLayoutManager.setFlexDirection(FlexDirection.COLUMN);
        flexboxLayoutManager.setAlignContent(AlignContent.FLEX_START);
        initHistoryData(allNames);
        initNetTags(allNames);
        initSuggestion();
        initSearchView();
    }

    private void initSuggestion() {

    }

    @Override
    public void onItemClick(AbstractRecyclerAdapter adapter, View view, int position) {
        Toast.makeText(getContext(), "你点我了==>" + adapter.getData().get(position), Toast.LENGTH_SHORT).show();
    }

    private void initNetTags(List<SearchModel> allNames) {
        mBinding.rvRecommend.setLayoutManager(flexboxLayoutManager);
        TagBindingAdapter adapter = new TagBindingAdapter(allNames);
        adapter.setOnItemClickListener(this);
        mBinding.rvRecommend.setAdapter(adapter);
    }

    private void initHistoryData(List<SearchModel> allNames) {
        mBinding.rvRecommend.setLayoutManager(flexboxLayoutManager);
        TagBindingAdapter adapter = new TagBindingAdapter(allNames);
        adapter.setOnItemClickListener(this);
        mBinding.rvRecommend.setAdapter(adapter);
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        if (query.isEmpty()) {
            Toast.makeText(getContext(), "不能为空!!!", Toast.LENGTH_SHORT).show();
            return false;
        }
        boolean contains = allNames.contains(query);
        if (!contains) {
            SearchModel searchModel = new SearchModel();
            searchModel.setName(query);
            allNames.add(searchModel);
            initHistoryData(allNames);
            SearchManger.getInstance(getContext()).putName(query);
        }
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return true;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

    }

    private void initAttr(Context context, AttributeSet attrs, int defStyleAttr) {

    }

    private void initSearchView() {
        SearchView searchView = mBinding.svSearch;
        //设置搜索框左边距
        LinearLayout editFrame = (LinearLayout) findViewById(R.id.search_edit_frame);
        LinearLayout.LayoutParams editP = (LayoutParams) editFrame.getLayoutParams();
        editP.leftMargin = 0;
        editP.rightMargin = 0;
        ImageView imageView = (ImageView) findViewById(R.id.search_mag_icon);
        imageView.setAdjustViewBounds(true);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        LinearLayout.LayoutParams lp3 = (LayoutParams) imageView.getLayoutParams();
        lp3.gravity = Gravity.CENTER_VERTICAL;
        lp3.leftMargin = (int) (DensityUtil.dip2px(8f) * DensityUtil.getBaseScale(getContext()));
        lp3.rightMargin = (int) (DensityUtil.dip2px(-2f) * DensityUtil.getBaseScale(getContext()));

        View view = searchView.findViewById(R.id.search_plate);
        view.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        EditText editText = (EditText) searchView.findViewById(R.id.search_src_text);
        editText.setBackgroundColor(Color.TRANSPARENT);
        editText.setTextSize(11.5f);
        editText.setTextColor(getResources().getColor(R.color.colorText));
        editText.setHintTextColor(getResources().getColor(R.color.colorHint));
        try {
            Field fCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
            fCursorDrawableRes.setAccessible(true);
            int mCursorDrawableRes = fCursorDrawableRes.getInt(editText);
            Field fEditor = TextView.class.getDeclaredField("mEditor");
            fEditor.setAccessible(true);
            Object editor = fEditor.get(editText);
            Class<?> clazz = editor.getClass();
            Field fCursorDrawable = clazz.getDeclaredField("mCursorDrawable");
            fCursorDrawable.setAccessible(true);
            if (mCursorDrawableRes <= 0) return;
            Drawable cursorDrawable = ContextCompat.getDrawable(searchView.getContext(), mCursorDrawableRes);
            if (cursorDrawable == null) return;
            Drawable tintDrawable = DrawableCompat.wrap(cursorDrawable);
            DrawableCompat.setTintList(tintDrawable, ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.bg_search)));
            Drawable[] drawables = new Drawable[]{tintDrawable, tintDrawable};
            fCursorDrawable.set(editor, drawables);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
