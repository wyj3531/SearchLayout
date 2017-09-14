package com.foretree.search;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by silen on 14/09/2017.
 */

public class SearchLayoutFactory {
    public static View createSearchFractory(Context context, LayoutType type) {
        View view = null;
        switch (type) {
            case FLEX:
                view = new FlexSearchLayout(context);
                view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                break;
            case FLOW:
                view = new FlowSearchLayout(context);
                view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                break;
        }
        return view;
    }
}
