package com.framgia.fdms.screen.marker;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.framgia.fdms.R;
import com.framgia.fdms.data.model.Producer;
import com.framgia.fdms.databinding.ItemMakerBinding;

import java.util.List;

/**
 * Created by beepi on 05/07/2017.
 */
public class MakerApdater extends RecyclerView.Adapter<MakerApdater.ViewHolder> {
    private List<Producer> mMakers;

    public MakerApdater(List<Producer> makers) {
        mMakers = makers;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemMakerBinding binding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_maker, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mMakers.get(position));
    }

    @Override
    public int getItemCount() {
        return mMakers == null ? 0 : mMakers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemMakerBinding mBinding;

        public ViewHolder(ItemMakerBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(Producer maker) {
            if (maker == null) {
                return;
            }
            mBinding.setMaker(maker);
        }
    }
}