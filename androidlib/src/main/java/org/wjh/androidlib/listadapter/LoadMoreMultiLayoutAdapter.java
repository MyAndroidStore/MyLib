package org.wjh.androidlib.listadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import org.wjh.androidlib.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Konfyt on 2016/9/14.
 */
public abstract class LoadMoreMultiLayoutAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> implements View.OnClickListener, View.OnLongClickListener {

    // 数据源
    private List<T> mDatas;
    // 布局填充器
    private LayoutInflater mInflater;
    // Attach的RecyclerView
    private RecyclerView mRecyclerView;
    // item的点击事件
    private OnItemClickListener mListener;
    private OnItemLongClickListener mLongListener;
    // error的点击事件
    private OnFooterErrorListener mErrorListener;
    // 上下文对象
    private Context mContext;


    // 脚布局
    private final int TYPE_FOOTER = Integer.MAX_VALUE;


    // 当前加载状态，默认为加载完成
    private int loadState = 2;


    // 第一次加载
    public final int LOAD_FIRST = 0;
    // 正在加载
    public final int LOADING = 1;
    // 加载完成
    private final int LOAD_COMPLETE = 2;
    // 加载到底
    private final int LOAD_END = 3;
    // 加载出错
    private final int LOAD_ERROR = 4;
    // 加载无数据
    private final int LOAD_NODATA = 5;

    // params
    private LinearLayout.LayoutParams wrapParams;
    private LinearLayout.LayoutParams matchParams;


    // 初始化无需数据源
    public LoadMoreMultiLayoutAdapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mDatas = new ArrayList<>();
        mContext = context;
        wrapParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        matchParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    // 初始化需数据源
    public LoadMoreMultiLayoutAdapter(Context context, List<T> list) {
        this(context);
        addData(list);
    }

    // 添加数据源
    public void addData(List<T> data) {
        mDatas.addAll(data);
        notifyDataSetChanged();
    }


    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为FooterView
        if (position == mDatas.size()) {
            return TYPE_FOOTER;
        } else {
            return getCustomItemViewType(position);
        }
    }


    // 多布局的ViewType
    public abstract int getCustomItemViewType(int position);

    // 获取item的总数量(数据源+脚布局)
    @Override
    public int getItemCount() {
        return mDatas.size() + 1;
    }

    // 创建ViewHolder


    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //进行判断显示类型，来创建返回不同的View
        if (viewType == TYPE_FOOTER) {
            View view = mInflater.inflate(R.layout.mylib_refresh_footer, parent, false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mErrorListener != null && loadState == LOAD_ERROR) {
                        mErrorListener.onClick();
                    }
                }
            });
            return new FootViewHolder(view);
        } else {
            RecyclerViewHolder viewHolder = onCreateCustomViewHolder(parent, viewType);
            viewHolder.itemView.setOnClickListener(this);
            viewHolder.itemView.setOnLongClickListener(this);
            return viewHolder;
        }
    }

    // 创建多布局的ViewHolder
    public abstract RecyclerViewHolder onCreateCustomViewHolder(ViewGroup parent, int viewType);


    // 绑定ViewHolder 需要定义抽象方法来实现里面的操作，
    // 所以LoadMoreLinearBaseAdapter需要声明成抽象类


    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        if (holder instanceof FootViewHolder) {
            FootViewHolder footViewHolder = (FootViewHolder) holder;
            FrameLayout layout = footViewHolder.getFrameLayout(R.id.mylib_layout);
            switch (loadState) {
                case LOAD_FIRST:
                    layout.setLayoutParams(matchParams);
                    layout.setBackgroundColor(0);
                    footViewHolder.getRelativeLayout(R.id.mylib_rl_first).setVisibility(View.VISIBLE);
                    footViewHolder.getRelativeLayout(R.id.mylib_rl_lading).setVisibility(View.GONE);
                    footViewHolder.getLinearLayout(R.id.mylib_ll_end).setVisibility(View.GONE);
                    footViewHolder.getRelativeLayout(R.id.mylib_rl_error).setVisibility(View.GONE);
                    footViewHolder.getRelativeLayout(R.id.mylib_ll_no_data).setVisibility(View.GONE);
                    break;
                case LOADING: // 正在加载
                    layout.setLayoutParams(wrapParams);
                    layout.setBackgroundColor(mContext.getResources().getColor(R.color.bg_color));
                    footViewHolder.getRelativeLayout(R.id.mylib_rl_first).setVisibility(View.GONE);
                    footViewHolder.getRelativeLayout(R.id.mylib_rl_lading).setVisibility(View.VISIBLE);
                    footViewHolder.getLinearLayout(R.id.mylib_ll_end).setVisibility(View.GONE);
                    footViewHolder.getRelativeLayout(R.id.mylib_rl_error).setVisibility(View.GONE);
                    footViewHolder.getRelativeLayout(R.id.mylib_ll_no_data).setVisibility(View.GONE);
                    break;

                case LOAD_COMPLETE: // 加载完成
                    layout.setLayoutParams(wrapParams);
                    layout.setBackgroundColor(0);
                    footViewHolder.getRelativeLayout(R.id.mylib_rl_first).setVisibility(View.GONE);
                    footViewHolder.getRelativeLayout(R.id.mylib_rl_lading).setVisibility(View.INVISIBLE);
                    footViewHolder.getLinearLayout(R.id.mylib_ll_end).setVisibility(View.GONE);
                    footViewHolder.getRelativeLayout(R.id.mylib_rl_error).setVisibility(View.GONE);
                    footViewHolder.getRelativeLayout(R.id.mylib_ll_no_data).setVisibility(View.GONE);
                    break;

                case LOAD_END: // 加载到底
                    layout.setLayoutParams(wrapParams);
                    layout.setBackgroundColor(mContext.getResources().getColor(R.color.bg_color));
                    footViewHolder.getRelativeLayout(R.id.mylib_rl_first).setVisibility(View.GONE);
                    footViewHolder.getRelativeLayout(R.id.mylib_rl_lading).setVisibility(View.GONE);
                    footViewHolder.getLinearLayout(R.id.mylib_ll_end).setVisibility(View.VISIBLE);
                    footViewHolder.getRelativeLayout(R.id.mylib_rl_error).setVisibility(View.GONE);
                    footViewHolder.getRelativeLayout(R.id.mylib_ll_no_data).setVisibility(View.GONE);
                    break;
                case LOAD_ERROR: // 加载出错
                    layout.setLayoutParams(wrapParams);
                    layout.setBackgroundColor(mContext.getResources().getColor(R.color.bg_color));
                    footViewHolder.getRelativeLayout(R.id.mylib_rl_first).setVisibility(View.GONE);
                    footViewHolder.getRelativeLayout(R.id.mylib_rl_lading).setVisibility(View.GONE);
                    footViewHolder.getLinearLayout(R.id.mylib_ll_end).setVisibility(View.GONE);
                    footViewHolder.getRelativeLayout(R.id.mylib_rl_error).setVisibility(View.VISIBLE);
                    footViewHolder.getRelativeLayout(R.id.mylib_ll_no_data).setVisibility(View.GONE);
                    break;
                case LOAD_NODATA:
                    layout.setLayoutParams(matchParams);
                    layout.setBackgroundColor(0);
                    footViewHolder.getRelativeLayout(R.id.mylib_rl_first).setVisibility(View.GONE);
                    footViewHolder.getRelativeLayout(R.id.mylib_rl_lading).setVisibility(View.GONE);
                    footViewHolder.getLinearLayout(R.id.mylib_ll_end).setVisibility(View.GONE);
                    footViewHolder.getRelativeLayout(R.id.mylib_rl_error).setVisibility(View.GONE);
                    footViewHolder.getRelativeLayout(R.id.mylib_ll_no_data).setVisibility(View.VISIBLE);
                    break;
            }
        } else {

            // 需要子类去实现 具体操作
            onBindCustomViewHolder(holder, mDatas.get(position), position);
        }
    }

    public abstract void onBindCustomViewHolder(RecyclerViewHolder holder, T t, int position);


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;

        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    // 如果当前是footer的位置，那么该item占据2个单元格，正常情况下占据1个单元格
                    return getItemViewType(position) == TYPE_FOOTER ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }


    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
        if (params != null && params instanceof StaggeredGridLayoutManager.LayoutParams
                && holder.getLayoutPosition() == mDatas.size()) {
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) params;
            layoutParams.setFullSpan(true);
        }
    }

    // 清空数据源
    public void clearAll() {
        mDatas.clear();
        notifyDataSetChanged();
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

    // 脚布局ViewHoldr类
    private static class FootViewHolder extends RecyclerViewHolder {

        FootViewHolder(View itemView) {
            super(itemView);
        }
    }


    /**
     * 设置加载的状态
     *
     * @param loadState 0.正在加载 1.加载完成 2.加载到底
     */
    public void setLoadState(LoadingState loadState) {
        this.loadState = loadState.getmState();
        notifyDataSetChanged();
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

    public void setOnItemLongClickListener(OnItemLongClickListener<T> listener) {
        this.mLongListener = listener;
    }

    // 对外提供设置footer的监听器的方法
    public void setOnFooterErrorListener(OnFooterErrorListener mErrorListener) {
        this.mErrorListener = mErrorListener;
    }
}