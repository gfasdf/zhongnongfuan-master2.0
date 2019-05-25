package com.zhongnongfuan.app.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhongnongfuan.app.R;
import com.zhongnongfuan.app.bean.MachineList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author qichaoqun
 * @date 2019/1/19
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> implements View.OnClickListener {

    private Context mContext = null;
    private MachineList mMachines = null;
    private OnRecyclerviewItemClickListener mOnItemClickListener = null;

    public RecyclerViewAdapter(Context context, MachineList machines) {
        mContext = context;
        mMachines = machines;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_view,
                viewGroup, false);
        view.setOnClickListener(this);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.machineName.setText(mMachines.getData().get(i).getMC());
        if ("报警".equals(mMachines.getData().get(i).getGzzt())) {
            myViewHolder.ivIsAlarm.setBackgroundResource(R.drawable.round_red);
        } else {
            myViewHolder.ivIsAlarm.setBackgroundResource(R.drawable.round_green);
        }
        myViewHolder.machineCondition.setText(mMachines.getData().get(i).getGzzt());
        myViewHolder.machineAddress.setText(mMachines.getData().get(i).getBZ());
        myViewHolder.itemView.setTag(i);
    }

    @Override
    public int getItemCount() {
        return mMachines == null ? 0 : mMachines.getData().size();
    }

    @Override
    public void onClick(View v) {
        mOnItemClickListener.onItemClickListener(v, ((int) v.getTag()));
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.machine_name)
        TextView machineName;
        @BindView(R.id.machine_condition)
        TextView machineCondition;
        @BindView(R.id.machine_address)
        TextView machineAddress;
        @BindView(R.id.iv_is_alarm)
        ImageView ivIsAlarm;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * 点击事件的回调方法
     *
     * @param itemClickListener 接口对象，用来实现的
     */
    public void setOnItemClickListener(OnRecyclerviewItemClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }

    public void flushList(MachineList list) {
        mMachines = list;
        notifyDataSetChanged();
    }
}
