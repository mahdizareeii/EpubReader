package com.test.app.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;

import com.test.R;
import com.test.app.model.CustomSpinnerModel;
import com.test.databinding.CustomSpinnerItemBinding;

import java.util.List;

public class CustomSpinnerAdapter extends BaseAdapter {

    private List<CustomSpinnerModel> list;
    private LayoutInflater layoutInflater;
    private CustomSpinnerItemBinding binding;

    public CustomSpinnerAdapter(Activity activity, List<CustomSpinnerModel> list) {
        this.list = list;
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            binding = DataBindingUtil.inflate(layoutInflater, R.layout.custom_spinner_item, parent, false);
        }
        binding.setSpinnerModel(list.get(position));
        return binding.getRoot();
    }
}
