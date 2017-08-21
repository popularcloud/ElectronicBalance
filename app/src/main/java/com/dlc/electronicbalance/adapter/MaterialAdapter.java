package com.dlc.electronicbalance.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dlc.electronicbalance.R;
import com.dlc.electronicbalance.bean.GetGoodsBean;
import com.dlc.electronicbalance.interfaces.ItemOnclick;

import java.util.List;

/**
 * Created by YoungeTao on 2017/8/9
 * QQ 2276559259.
 * gmail youngetao@gmail.com
 */

public class MaterialAdapter extends RecyclerView.Adapter<MaterialAdapter.MyViewHolder>{

    private Context context;
    ItemOnclick itemOnClick;
    private View selectView;
    private List<GetGoodsBean.DataBean> mGoodsList;
    public MaterialAdapter(Context context, List<GetGoodsBean.DataBean> mGoodsList){
        this.context = context;
        this.mGoodsList = mGoodsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_material, parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tv_number.setText(String.valueOf(position+1));
        holder.tv_name.setText(mGoodsList.get(position).name);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectView != null) {
                    selectView.setSelected(false);
                }
                v.setSelected(true);
                selectView = v;
                if(itemOnClick != null){
                    itemOnClick.onClick(holder.itemView,mGoodsList.get(position));
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mGoodsList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv_number;
        TextView tv_name;
        public MyViewHolder(View view)
        {
            super(view);
            tv_number = (TextView) view.findViewById(R.id.tv_number);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
        }
    }

    public void setOnItemOnClick(ItemOnclick itemOnClick) {
        this.itemOnClick = itemOnClick;
    }
}
