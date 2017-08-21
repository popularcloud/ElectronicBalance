package com.dlc.electronicbalance.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dlc.electronicbalance.R;
import com.dlc.electronicbalance.interfaces.ItemOnclick;

/**
 * Created by YoungeTao on 2017/8/10
 * QQ 2276559259.
 * gmail youngetao@gmail.com
 */

public class PackageDataAdapter extends RecyclerView.Adapter<PackageDataAdapter.MyViewHolder>{

    private Context context;
    ItemOnclick itemOnClick;
    private View selectView;
    public PackageDataAdapter(Context context){
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_package_data, parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tv_number.setText(String.valueOf(position+1));
        holder.tv_name.setText("料品"+(position+1));
        holder.tv_printing_message.setText("456465456");
        holder.tv_staff_number.setText("10000"+(position+1));
        holder.tv_staff.setText("周欣欣"+(position+1));
        holder.tv_count.setText("100包");
        holder.tv_time.setText("2017.08.04 14:59");

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
        return 20;
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv_number;
        TextView tv_name;
        TextView tv_printing_message;
        TextView tv_staff_number;
        TextView tv_staff;
        TextView tv_count;
        TextView tv_time;
        public MyViewHolder(View view)
        {
            super(view);
            tv_number = (TextView) view.findViewById(R.id.tv_number);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_printing_message = (TextView) view.findViewById(R.id.tv_printing_message);
            tv_staff_number = (TextView) view.findViewById(R.id.tv_staff_number);
            tv_staff = (TextView) view.findViewById(R.id.tv_staff);
            tv_count = (TextView) view.findViewById(R.id.tv_count);
            tv_time = (TextView) view.findViewById(R.id.tv_time);

        }
    }

    public void setOnItemOnClick(ItemOnclick itemOnClick) {
        this.itemOnClick = itemOnClick;
    }
}
