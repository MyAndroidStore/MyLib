package org.wjh.androidlib.listadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Konfyt on 2016/9/14.
 */
public abstract class SimpleMultiLayoutAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> implements View.OnClickListener, View.OnLongClickListener {

    // 数据源
    private List<T> mDatas;
    // 布局填充器
    private LayoutInflater mInflater;
    // Attach的RecyclerView
    private RecyclerView mRecyclerView;
    // item的点击事件
    private OnItemClickListener mListener;
    private OnItemLongClickListener mLongListener;
    // 上下文对象
    private Context mContext;


    // 初始化无需数据源
    public SimpleMultiLayoutAdapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mDatas = new ArrayList<>();
        mContext = context;
    }

    // 初始化需数据源
    public SimpleMultiLayoutAdapter(Context context, List<T> list) {
        this(context);
        addData(list);
    }

    // 添加数据源
    public void addData(List<T> data) {

        if (data == null)
            return;

        mDatas.addAll(data);
        notifyDataSetChanged();
    }


    // 获取item的总数量(数据源+脚布局)
    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        return getCustomItemViewType(position);
    }

    // 多布局的ViewType
    public abstract int getCustomItemViewType(int position);


    // 创建ViewHolder


    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerViewHolder viewHolder = onCreateCustomViewHolder(parent, viewType);
        viewHolder.itemView.setOnClickListener(this);
        viewHolder.itemView.setOnLongClickListener(this);
        return viewHolder;
    }

    // 创建多布局的ViewHolder
    public abstract RecyclerViewHolder onCreateCustomViewHolder(ViewGroup parent, int viewType);

    // 绑定ViewHolder 需要定义抽象方法来实现里面的操作，
    // 所以LoadMoreLinearBaseAdapter需要声明成抽象类


    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        // 需要子类去实现 具体操作
        bind(holder, mDatas.get(position), position);
    }

    // 清空数据源
    public void clearAll() {
        mDatas.clear();
        notifyDataSetChanged();
    }


    public abstract void bind(RecyclerViewHolder holder, T t, int position);

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }

    @Override
    public void onClick(View v) {
        int position = mRecyclerView.getChildAdapterPosition(v);
        T t = mDatas.get(position);
        if (mListener != null) {
            mListener.onClick(t, position, v);
        }
    }

    @Override
    public boolean onLongClick(View view) {
        int position = mRecyclerView.getChildAdapterPosition(view);
        T t = mDatas.get(position);
        if (mLongListener != null) {
            mLongListener.onLongClick(t, position, view);
        }
        return mLongListener != null;
    }


    // 对外提供获取数据源的方法
    public List<T> getAttachDatas() {
        return mDatas;
    }

    // 对外提供获取context的方法
    public Context getAttachContext() {
        return mContext;
    }

    // 对外提供获取布局填充器的方法
    public LayoutInflater getAttachInflater() {
        return mInflater;
    }


    // 设置Item的点击事件
    public void setOnItemClickListener(OnItemClickListener<T> listener) {
        this.mListener = listener;
    }

    public void setOnLongItemClickListener(OnItemLongClickListener<T> listener) {
        this.mLongListener = listener;
    }

}