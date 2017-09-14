package com.foretree.search.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;


public abstract class DataBindingRecyclerAdapter<T, B extends ViewDataBinding> extends AbstractRecyclerAdapter<T, DataBindingRecyclerHolder<B>> {

    public DataBindingRecyclerAdapter(List<T> data) {
        super(data);
    }

    @Override
    public DataBindingRecyclerHolder<B> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DataBindingRecyclerHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), getItemLayoutId(), parent, false));
    }

    protected abstract int getItemLayoutId();

}
