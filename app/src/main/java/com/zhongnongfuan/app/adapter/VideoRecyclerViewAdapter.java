package com.zhongnongfuan.app.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhongnongfuan.app.R;
import com.zhongnongfuan.app.bean.MonitorBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoRecyclerViewAdapter extends RecyclerView.Adapter<VideoRecyclerViewAdapter.MyViewHolder> implements View.OnClickListener {


    private Context mContext = null;
    private List<MonitorBean.DataBean> mMonitorList = null;
    private OnRecyclerviewItemClickListener mOnItemClickListener = null;

    public VideoRecyclerViewAdapter(Context context, List<MonitorBean.DataBean> mMonitorList) {
        mContext = context;
        this.mMonitorList = mMonitorList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.video_item_view,
                viewGroup, false);
        view.setOnClickListener(this);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Log.i("", "onBindViewHolder: 渲染每一项：：：：： 第 " + i + "项值为 ： " + mMonitorList.get(i).toString());
        myViewHolder.monitorName.setText(mMonitorList.get(i).getMC());
        myViewHolder.monitorNote.setText("备注：" + mMonitorList.get(i).getBZ());
        myViewHolder.monitorJgcName.setText("加工厂：" + mMonitorList.get(i).getJGCMC());
        myViewHolder.monitorLocation.setText("位置：" + mMonitorList.get(i).getWZ());
        myViewHolder.itemView.setTag(i);
    }

    @Override
    public int getItemCount() {
        return mMonitorList == null ? 0 : mMonitorList.size();
    }

    @Override
    public void onClick(View v) {
        mOnItemClickListener.onItemClickListener(v, ((int) v.getTag()));
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.monitor_name)
        TextView monitorName;
        @BindView(R.id.monitor_note)
        TextView monitorNote;
        @BindView(R.id.monitor_jgc_name)
        TextView monitorJgcName;
        @BindView(R.id.monitor_location)
        TextView monitorLocation;

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

    public void flushList(List<MonitorBean.DataBean> list) {
        mMonitorList = list;
        notifyDataSetChanged();
    }
}
