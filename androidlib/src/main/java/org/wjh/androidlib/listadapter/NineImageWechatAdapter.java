package org.wjh.androidlib.listadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.wjh.androidlib.R;
import org.wjh.androidlib.exceptions.MoreThanNineImageException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Konfyt on 2016/9/14.
 */
public abstract class NineImageWechatAdapter extends RecyclerView.Adapter<NineImageWechatAdapter.ViewHolder> implements View.OnClickListener, View.OnLongClickListener {

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

        // 最后一个item设置为FooterView
        if (position == mDatas.size() && mDatas.size() > 1) {
            // 获取数据源最后一条数据
            NineImageUrl nineImageUrl = mDatas.get(position);
            if (nineImageUrl.getResId() == 0 || nineImageUrl.getResId() != R.drawable.mylib_nine_image_add)
                return TYPE_ITEM;
            else
                return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    // 获取item的总数量(数据源9个 不添加脚布局)
    @Override
    public int getItemCount() {
        return mDatas.size() == 9 ? 9 : mDatas.size() + 1;
    }

    // 创建ViewHolder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

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
    public void onBindViewHolder(ViewHolder holder, int position) {

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
    private static class FootViewHolder extends NineImageWechatAdapter.ViewHolder {

        FootViewHolder(View itemView) {
            super(itemView);
        }
    }

    private static class NormalViewHolder extends NineImageWechatAdapter.ViewHolder {

        NormalViewHolder(View itemView) {
            super(itemView);
        }
    }

    // ViewHoldr类
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private Map<Integer, View> mCacheViews;

        public ViewHolder(View itemView) {
            super(itemView);
            mCacheViews = new HashMap<>();
        }


        //获得常用控件
        public ImageView getImageView(int id) {
            return getView(id);
        }

        public TextView getTextView(int id) {
            return getView(id);
        }

        public EditText getEditText(int id) {
            return getView(id);
        }

        public Button getButton(int id) {
            return getView(id);
        }

        public ImageButton getImageButton(int id) {
            return getView(id);
        }

        public CheckBox getCheckBox(int id) {
            return getView(id);
        }

        public ProgressBar getProgressBar(int id) {
            return getView(id);
        }

        public LinearLayout getLinearLayout(int id) {
            return getView(id);
        }

        public RelativeLayout getRelativeLayout(int id) {
            return getView(id);
        }

        public FrameLayout getFrameLayout(int id) {
            return getView(id);
        }

        public CircleImageView getCircleImageView(int id) {
            return getView(id);
        }

        public <T extends View> T getView(int resId) {
            View view = null;
            if (mCacheViews.containsKey(resId)) {
                view = mCacheViews.get(resId);
            } else {
                view = itemView.findViewById(resId);
                mCacheViews.put(resId, view);
            }
            return (T) view;
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

    public interface OnItemClickListener {
        // 传递当前点击的对象（List对应位置的数据）与位置
        void onClick(NineImageUrl t, int position, View view);
    }

    public interface OnItemLongClickListener {
        // 传递当前点击的对象（List对应位置的数据）与位置
        void onLongClick(NineImageUrl t, int position, View view);
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