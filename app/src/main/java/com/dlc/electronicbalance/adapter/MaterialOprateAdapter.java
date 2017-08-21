package com.dlc.electronicbalance.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dlc.electronicbalance.R;
import com.dlc.electronicbalance.bean.GetGoodsBean;
import com.dlc.electronicbalance.interfaces.ItemBtnCallBack;
import com.dlc.electronicbalance.interfaces.ItemOnclick;

import java.util.List;

/**
 * Created by YoungeTao on 2017/8/9
 * QQ 2276559259.
 * gmail youngetao@gmail.com
 */

public class MaterialOprateAdapter extends RecyclerView.Adapter<MaterialOprateAdapter.MyViewHolder>{

    private Context context;
    ItemOnclick itemOnClick;
    ItemBtnCallBack itemBtnClick;
    private List<GetGoodsBean.DataBean> mGoodsList;
    private View selectView;
    public MaterialOprateAdapter(Context context,List<GetGoodsBean.DataBean> mGoodsList){
        this.context = context;
        this.mGoodsList = mGoodsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_material_oprate, parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tv_number.setText(String.valueOf(position+1));
        holder.tv_name.setText(mGoodsList.get(position).name);

        holder.tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemBtnClick != null){
                    itemBtnClick.onEdit(v,String.valueOf(position));
                }
            }
        });
        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemBtnClick != null){
                    itemBtnClick.onDelete(v,String.valueOf(position));
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
        TextView tv_edit;
        TextView tv_delete;
        public MyViewHolder(View view)
        {
            super(view);
            tv_number = (TextView) view.findViewById(R.id.tv_number);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_edit = (TextView) view.findViewById(R.id.tv_edit);
            tv_delete = (TextView) view.findViewById(R.id.tv_delete);
        }
    }

    public void setOnItemOnClick(ItemOnclick itemOnClick) {
        this.itemOnClick = itemOnClick;
    }

    public void setItemBtnClick(ItemBtnCallBack itemBtnClick) {
        this.itemBtnClick = itemBtnClick;
    }
}
