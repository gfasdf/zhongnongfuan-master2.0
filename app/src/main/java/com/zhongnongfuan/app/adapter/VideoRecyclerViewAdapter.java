package com.zhongnongfuan.app.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ezvizuikit.open.EZUIPlayer;
import com.zhongnongfuan.app.R;
import com.zhongnongfuan.app.activity.DoublePlayActivity;
import com.zhongnongfuan.app.activity.PlayActivity;
import com.zhongnongfuan.app.activity.PlayBackActivity;
import com.zhongnongfuan.app.bean.MonitorBean;
import com.zhongnongfuan.app.utils.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoRecyclerViewAdapter extends RecyclerView.Adapter<VideoRecyclerViewAdapter.MyViewHolder> implements View.OnClickListener {


    private Context mContext = null;
    private MonitorBean mMonitorBean;
    private OnRecyclerviewItemClickListener mOnItemClickListener = null;
    public static String AppKey = "945bbd856cd84ea599fec4241f0ed632";

    public VideoRecyclerViewAdapter(Context context,  MonitorBean mMonitorBean) {
        mContext = context;
        this.mMonitorBean = mMonitorBean;
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
        Log.i("", "onBindViewHolder: 渲染每一项：：：：： 第 " + i + "项值为 ： " +  mMonitorBean.getData().get(i).toString());
        myViewHolder.monitorName.setText(mMonitorBean.getData().get(i).getMC());
        myViewHolder.monitorNote.setText("备注：" + mMonitorBean.getData().get(i).getBZ());
        myViewHolder.monitorJgcName.setText("加工厂：" + mMonitorBean.getData().get(i).getJGCMC());
        myViewHolder.monitorLocation.setText(mMonitorBean.getData().get(i).getWZ());
        myViewHolder.itemView.setTag(i);
    }

    @Override
    public int getItemCount() {
        if (mMonitorBean == null){
            return 0;
        }
        return mMonitorBean.getData() == null ? 0 : mMonitorBean.getData().size();
    }

    @Override
    public void onClick(View v) {
        if (CommonUtils.isFastDoubleClick()) {
            Toast.makeText(mContext, "操作过于频繁", Toast.LENGTH_SHORT).show();
        } else {
            Log.i("", "onItemClickListener: 点击监控列表中某一项：：：：：：：： " + v.getTag());
            imitatePlayButton("ezopen://"+mMonitorBean.getData().get((Integer) v.getTag()).getYZM()+"@open.ys7.com/" + mMonitorBean.getData().get((Integer) v.getTag()).getXLH() + "/1.hd.live", AppKey,mMonitorBean.getAccesstoken());
        }
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

    public void flushList(MonitorBean monitorBean) {
        mMonitorBean = monitorBean;
        notifyDataSetChanged();
    }


    public void imitatePlayButton(String mUrl, String mAppKey, String mAccessToken) {
        String[] urls = mUrl.split(",");
        Log.e("", "onClick: 切割后产生的urls为：：：：：" + urls);
        if (urls != null && urls.length == 2) {
            //启动普通回放页面
            DoublePlayActivity.startPlayActivity(mContext, mAppKey, mAccessToken, urls[0], urls[1]);
            return;
        }

        EZUIPlayer.EZUIKitPlayMode mode = null;
        mode = EZUIPlayer.getUrlPlayType(mUrl);
        if (mode == EZUIPlayer.EZUIKitPlayMode.EZUIKIT_PLAYMODE_LIVE) {
            //启动播放页面
            PlayActivity.startPlayActivity(mContext, mAppKey, mAccessToken, mUrl);
        } else if (mode == EZUIPlayer.EZUIKitPlayMode.EZUIKIT_PLAYMODE_REC) {
            //默认启动动回放带时间轴页面
            PlayBackActivity.startPlayBackActivity(mContext, mAppKey, mAccessToken, mUrl);
        } else {
            Toast.makeText(mContext, "播放模式未知，默认进入直播预览模式", Toast.LENGTH_LONG).show();
            //启动播放页面
            PlayActivity.startPlayActivity(mContext, mAppKey, mAccessToken, mUrl);
        }
    }
}
