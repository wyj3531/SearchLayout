package com.foretree.search.adapter;

import com.foretree.search.R;
import com.foretree.search.SearchModel;
import com.foretree.search.databinding.ItemBindingTagBinding;

import java.util.List;

/**
 * Created by silen on 09/09/2017.
 */

public class TagBindingAdapter extends DataBindingRecyclerAdapter<SearchModel,ItemBindingTagBinding> {

    public TagBindingAdapter(List<SearchModel> data) {
        super(data);
    }

    @Override
    public void onBindViewHolder(DataBindingRecyclerHolder<ItemBindingTagBinding> holder, int position, SearchModel item) {
        holder.binding.setModel(item);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_binding_tag;
    }
}
