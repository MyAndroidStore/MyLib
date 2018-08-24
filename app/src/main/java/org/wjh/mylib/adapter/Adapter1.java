package org.wjh.mylib.adapter;

import android.content.Context;

import org.wjh.androidlib.listadapter.LoadMoreSingleLayoutAdapter;
import org.wjh.androidlib.textview.PraiseTextView;
import org.wjh.androidlib.utils.ToastUtils;
import org.wjh.mylib.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter1 extends LoadMoreSingleLayoutAdapter<String>{


    public Adapter1(Context context, int layoutResId) {
        super(context, layoutResId);
    }

    @Override
    public void bind(ViewHolder holder, String s, int position) {
        PraiseTextView textView = (PraiseTextView) holder.getTextView(R.id.pt);

        List<PraiseTextView.PraiseInfo> list = new ArrayList<>();

        for (int i = 0; i < 10; i++) {

            PraiseTextView.PraiseInfo info = new PraiseTextView.PraiseInfo("mark","被奔跑的蜗牛");

            list.add(info);
        }
        textView.setData(list);

        textView.setOnPraiseListener(new PraiseTextView.OnPraiseClickListener() {
            @Override
            public void onClick(int position, PraiseTextView.PraiseInfo mPraiseInfo) {
                ToastUtils.getInstance().shortToast(mPraiseInfo.getNickname());
            }
        });
    }
}
