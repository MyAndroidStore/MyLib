package org.wjh.androidlib.listadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.wjh.androidlib.R;
import org.wjh.androidlib.exceptions.MoreThanNineImageException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Konfyt on 2016/9/14.
 */
public abstract class NineImageWechatAdapter extends RecyclerView.Adapter<RecyclerViewHolder> implements View.OnClickListener, View.OnLongClickListener {

    // 数据源(图片选择的本地地址)
    private List<NineImageUrl> mDatas;
    // 布局填充器
    private LayoutInflater mInflater;
    // Attach的RecyclerView
    private RecyclerView mRecyclerView;
    // item的点击事件
    private OnItemClickListener mListener;
    private OnItemLongClickListener mLongListener;
    // error的点击事件
    private OnAddOneImageListener mOnAddOneImageListener;
    // 上下文对象
    private Context mContext;
    private ImageLoader mImageLoader;


    // 默认布局
    private final int TYPE_ITEM = 1;
    // 脚布局
    private final int TYPE_FOOTER = 2;


    // 初始化无需数据源
    public NineImageWechatAdapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mDatas = new ArrayList<>();
        mContext = context;
        mImageLoader = initImageLoader();

        notifyDataSetChanged();
    }

    protected abstract ImageLoader initImageLoader();


    // 添加数据源
    public void addData(List<NineImageUrl> data) {

        if (getAttachDatas().size() + data.size() > 9)
            throw new MoreThanNineImageException();
        else {
            mDatas.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void deleteOneData(int pos) {

        mDatas.remove(pos);
        notifyDataSetChanged();
    }


    @Override
    public int getItemViewType(int position) {

        // size==9特殊处理
        if (mDatas.size() == 9) {
            return TYPE_ITEM;
        } else {
            if (position == mDatas.size()) {
                return TYPE_FOOTER;
            } else {
                return TYPE_ITEM;
            }
        }
    }

    // 获取item的总数量(数据源9个 不添加脚布局)
    @Override
    public int getItemCount() {
        return mDatas.size() == 9 ? 9 : mDatas.size() + 1;
    }

    // 创建ViewHolder

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //进行判断显示类型，来创建返回不同的View
        if (viewType == TYPE_FOOTER) {
            View view = mInflater.inflate(R.layout.mylib_nine_image_layout, parent, false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnAddOneImageListener != null) {
                        mOnAddOneImageListener.onClick();
                    }
                }
            });
            return new FootViewHolder(view);
        } else {
            View itemView = mInflater.inflate(R.layout.mylib_nine_image_layout, parent, false);
            // 设置item的点击事件
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            return new NormalViewHolder(itemView);
        }
    }

    // 绑定ViewHolder 需要定义抽象方法来实现里面的操作，
    // 所以LoadMoreLinearBaseAdapter需要声明成抽象类


    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        if (holder instanceof FootViewHolder) {
            FootViewHolder footViewHolder = (FootViewHolder) holder;
            mImageLoader.displayImage(getAttachContext(), R.drawable.mylib_nine_image_add, footViewHolder.getImageView(R.id.mylib_nine_img));
        } else {

            NormalViewHolder normalViewHolder = (NormalViewHolder) holder;
            NineImageUrl nineImageUrl = getAttachDatas().get(position);

            if (TextUtils.isEmpty(nineImageUrl.getLocalUrl()))
                mImageLoader.displayImage(getAttachContext(), nineImageUrl.getResId(), normalViewHolder.getImageView(R.id.mylib_nine_img));
            else
                mImageLoader.displayImage(getAttachContext(), nineImageUrl.getLocalUrl(), normalViewHolder.getImageView(R.id.mylib_nine_img));
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }

    @Override
    public void onClick(View v) {
        int position = mRecyclerView.getChildAdapterPosition(v);
        NineImageUrl t = mDatas.get(position);
        if (mListener != null) {
            mListener.onClick(t, position, v);
        }
    }

    @Override
    public boolean onLongClick(View view) {
        int position = mRecyclerView.getChildAdapterPosition(view);
        NineImageUrl t = mDatas.get(position);
        if (mLongListener != null) {
            mLongListener.onLongClick(t, position, view);
        }
        return mLongListener != null;
    }

    // 脚布局ViewHoldr类
    private static class FootViewHolder extends RecyclerViewHolder {

        FootViewHolder(View itemView) {
            super(itemView);
        }
    }

    private static class NormalViewHolder extends RecyclerViewHolder {

        NormalViewHolder(View itemView) {
            super(itemView);
        }
    }


    // 对外提供获取数据源的方法
    public List<NineImageUrl> getAttachDatas() {
        return mDatas;
    }

    // 对外提供获取context的方法
    public Context getAttachContext() {
        return mContext;
    }


    // 设置Item的点击事件
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.mLongListener = listener;
    }

    // 对外提供设置添加图片的监听器的方法
    public void setOnAddOneImageListener(OnAddOneImageListener onAddOneImageListener) {
        this.mOnAddOneImageListener = onAddOneImageListener;
    }

    // foot 添加图片时间
    public interface OnAddOneImageListener {
        void onClick();
    }

    public interface ImageLoader extends Serializable {
        void displayImage(Context context, String path, ImageView imageView);

        void displayImage(Context context, int resID, ImageView imageView);
    }
}