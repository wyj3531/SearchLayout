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
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.foretree.search.databinding.LayoutSearchBinding;
import com.foretree.search.view.FlowLayout;
import com.foretree.search.view.TagAdapter;
import com.foretree.search.view.TagFlowLayout;

import java.lang.reflect.Field;
import java.util.List;


/**
 * Created by silen on 11/08/2017.
 */

public class FlowSearchLayout extends LinearLayout
        implements View.OnFocusChangeListener, SearchView.OnQueryTextListener {
    LayoutSearchBinding mBinding;
    private LayoutInflater inflater;
    private List<String> allNames;


    public class Presenter {
        public void cancelSearch(View view) {

        }

        public void clearNames(View view) {
            boolean b = SearchManger.getInstance(getContext()).clearAllNames();
            if (b) initHistoryData(allNames);
        }
    }

    public FlowSearchLayout(Context context) {
        this(context, null);
    }

    public FlowSearchLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowSearchLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUp(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FlowSearchLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setUp(context, attrs, defStyleAttr);
    }

    private void setUp(Context context, AttributeSet attrs, int defStyleAttr) {
        inflater = LayoutInflater.from(context);
        mBinding = DataBindingUtil.inflate(inflater, R.layout.layout_search, this, true);
        mBinding.setPresenter(new Presenter());
        allNames = SearchManger.getInstance(getContext()).getAllNames();
        initAttr(context, attrs, defStyleAttr);
        mBinding.svSearch.setOnQueryTextListener(this);
        mBinding.svSearch.setOnQueryTextFocusChangeListener(this);
        initHistoryData(allNames);
        initNetTags(allNames);
        initSuggestion();
        initSearchView();
    }

    private void initSuggestion() {

    }

    private void initNetTags(List<String> allNames) {
        TagFlowLayout flowLayoutRecommend = mBinding.flowLayoutRecommend;
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

    private void initHistoryData(List<String> allNames) {
        int padding = (int) (DensityUtil.dip2px(10f) * DensityUtil.getBaseScale(getContext()));
        TagAdapter adapter = new TagAdapter<String>(allNames) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView textView = new TextView(getContext());
                textView.setLayoutParams(new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                textView.setWidth((int) (DensityUtil.dip2px(80f) * DensityUtil.getBaseScale(getContext())));
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
        if (query.isEmpty()) {
            Toast.makeText(getContext(), "不能为空!!!", Toast.LENGTH_SHORT).show();
            return false;
        }
        boolean contains = allNames.contains(query);
        if (!contains) {
            allNames.add(query);
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
