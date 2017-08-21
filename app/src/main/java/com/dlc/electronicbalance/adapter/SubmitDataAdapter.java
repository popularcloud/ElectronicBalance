package com.dlc.electronicbalance.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dlc.electronicbalance.R;
import com.dlc.electronicbalance.database.WeighDatasBean;
import com.dlc.electronicbalance.interfaces.ItemOnclick;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YoungeTao on 2017/8/10
 * QQ 2276559259.
 * gmail youngetao@gmail.com
 */

public class SubmitDataAdapter extends RecyclerView.Adapter<SubmitDataAdapter.MyViewHolder>{

    private Context context;
    ItemOnclick itemOnClick;
    private View selectView;
    private List<WeighDatasBean> dataList;

    public SubmitDataAdapter(Context context){
        this.context = context;
        dataList = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_submit_data, parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        WeighDatasBean bean = dataList.get(position);
        holder.tv_number.setText(String.valueOf(position+1));
        holder.tv_name.setText("料品"+bean.getGoods_name());
        holder.tv_weight.setText(bean.getNums());
        holder.tv_staff_number.setText(bean.getU_account());
        holder.tv_staff.setText(bean.getU_name());
        holder.tv_time.setText(bean.getRq());

  /*      holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectView != null) {
                    selectView.setSelected(false);
                }
                v.setSelected(true);
                selectView = v;
                if(itemOnClick != null){
                    itemOnClick.onClick(holder.itemView, String.valueOf(position));
                }

            }
        });*/
    }

    @Override
    public int getItemCount() {
        return dataList==null?0:dataList.size();
    }

    public void setDataList(List<WeighDatasBean> dataList){
        if(dataList != null){
            this.dataList.addAll(dataList);
        }
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv_number;
        TextView tv_name;
        TextView tv_weight;
        TextView tv_staff_number;
        TextView tv_staff;
        TextView tv_time;
        public MyViewHolder(View view)
        {
            super(view);
            tv_number = (TextView) view.findViewById(R.id.tv_number);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_weight = (TextView) view.findViewById(R.id.tv_weight);
            tv_staff_number = (TextView) view.findViewById(R.id.tv_staff_number);
            tv_staff = (TextView) view.findViewById(R.id.tv_staff);
            tv_time = (TextView) view.findViewById(R.id.tv_time);

        }
    }

    public void setOnItemOnClick(ItemOnclick itemOnClick) {
        this.itemOnClick = itemOnClick;
    }
}
