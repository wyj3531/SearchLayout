package com.foretree.search.adapter;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;


public class DataBindingRecyclerHolder<B extends ViewDataBinding> extends RecyclerView.ViewHolder {
    public B binding;

    public DataBindingRecyclerHolder(B binding) {
        this(binding.getRoot());
        this.binding = binding;
    }


    public DataBindingRecyclerHolder(View v) {
        super(v);
    }
}
