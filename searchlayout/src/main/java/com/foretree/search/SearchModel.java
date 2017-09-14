package com.foretree.search;

import android.databinding.BaseObservable;
import android.databinding.Bindable;


/**
 * Created by silen on 11/08/2017.
 */

public class SearchModel extends BaseObservable {
    private String name;
    private boolean checked;

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(com.foretree.search.BR.name);
    }
}
