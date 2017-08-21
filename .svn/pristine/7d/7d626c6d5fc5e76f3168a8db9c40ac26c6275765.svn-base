package com.dlc.electronicbalance.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dlc.electronicbalance.R;
import com.dlc.electronicbalance.bean.GetGroupBean;
import com.dlc.electronicbalance.interfaces.ItemOnclick;

import java.util.List;

/**
 * Created by YoungeTao on 2017/8/9
 * QQ 2276559259.
 * gmail youngetao@gmail.com
 */

public class MaterialTypeAdapter extends RecyclerView.Adapter<MaterialTypeAdapter.MyViewHolder> {

    private Context context;
    ItemOnclick itemOnClick;
    private View selectView;
    private List<GetGroupBean.DataBean> mData;

    public MaterialTypeAdapter(Context context,List<GetGroupBean.DataBean> mData) {
        this.context = context;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_material_type, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tv_name.setText(mData.get(position).name);
        holder.tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectView != null) {
                    selectView.setSelected(false);
                }
                v.setSelected(true);
                selectView = v;
                if(itemOnClick != null){
                    itemOnClick.onClick(holder.tv_name, mData.get(position).id);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;

        public MyViewHolder(View view) {
            super(view);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
        }
    }

    public void setOnItemOnClick(ItemOnclick itemOnClick) {
        this.itemOnClick = itemOnClick;
    }

}
