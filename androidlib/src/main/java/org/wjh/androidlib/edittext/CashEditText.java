package org.wjh.androidlib.edittext;

import android.content.Context;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 现金输入的editText
 */
public class CashEditText extends android.support.v7.widget.AppCompatEditText {


    public CashEditText(Context context) {
        super(context);
        noClick();
        setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
    }

    public CashEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        noClick();
        setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
    }

    public CashEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        noClick();
        setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
    }

    /**
     * 设置最大金额以及监听
     */
    public void setMaxCashAndEditListener(double max, EditListener listener) {
        InputFilter[] inputFilter = new InputFilter[1];
        inputFilter[0] = new CashierInputFilter(max, listener);
        this.setFilters(inputFilter);
    }


    /**
     * 禁用双击以及长按弹出 复制、全选菜单
     */
    private void noClick() {

        this.setLongClickable(false);
        this.setTextIsSelectable(false);

        this.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {

            }
        });
    }


    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        //保证光标始终在最后面
        if (selStart == selEnd) {//防止不能多选
            setSelection(getText().length());
        }
    }

    @Override
    public boolean onTextContextMenuItem(int id) {
        return true;
    }


    /**
     * 输入类型限制为现金格式
     */
    class CashierInputFilter implements InputFilter {

        Pattern mPattern;

        //输入的最大金额
        private final double MAX_VALUE;
        //小数点后的位数
        private static final int POINTER_LENGTH = 2;

        private static final String POINTER = ".";

        private static final String ZERO = "0";

        private EditListener editListener;

        public CashierInputFilter(double MAX_VALUE, EditListener editListener) {
            mPattern = Pattern.compile("([0-9]|\\.)*");
            this.MAX_VALUE = MAX_VALUE;
            this.editListener = editListener;
        }

        /**
         * @param source 新输入的字符串
         * @param start  新输入的字符串起始下标，一般为0
         * @param end    新输入的字符串终点下标，一般为source长度-1
         * @param dest   输入之前文本框内容
         * @param dstart 要替换或者添加的起始位置，即光标所在的位置
         * @param dend   原内容终点坐标，若为选择一串字符串进行更改，则为选中字符串 最后一个字符在dest中的位置。
         * @return 替换光标所在位置的CharSequence
         */
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            // 新输入的字符串
            String sourceText = source.toString();
            // 输入前的字符串
            String destText = dest.toString();

            //删除操作
            if (TextUtils.isEmpty(sourceText) && dend > dstart) {

                // 全部清空
                if (dend == 1) {

                    if (editListener != null)
                        editListener.correct(0.00);

                } else {
                    // 需要验证金额
                    double sumText = Double.parseDouble(destText.substring(0, dstart));
                    if (sumText > MAX_VALUE && editListener != null) {
                        editListener.error("超出可转金额上限");
                    }

                    if (sumText <= MAX_VALUE && editListener != null) {
                        editListener.correct(sumText);
                    }
                }
                return "";
            }

            Matcher matcher = mPattern.matcher(source);
            // 不符合正则表达式
            if (!matcher.matches()) {
                return "";
            }
            //已经输入小数点的情况下，只能输入数字
            if (destText.contains(POINTER)) {

                if (sourceText.equals(POINTER)) {  //只能输入一个小数点
                    return "";
                }

                //验证小数点精度，保证小数点后只能输入两位
                int pointIndex = destText.indexOf(POINTER);

                if (dend - pointIndex > POINTER_LENGTH) {
                    return "";
                }
            } else {
                //没有输入小数点的情况下，只能输入小数点和数字，但首位不能输入小数点
                if (dstart == 0 && sourceText.equals(POINTER)) {
                    return "";
                }
                //没有输入小数点的情况下，首位是0只能输入小数点
                if (destText.equals(ZERO) && !sourceText.equals(POINTER)) {
                    return "";
                }
            }

            //验证输入金额的大小
            double sumText = Double.parseDouble(destText + sourceText);
            if (sumText > MAX_VALUE && editListener != null) {
                editListener.error("超出可转金额上限");
            }

            if (sumText <= MAX_VALUE && editListener != null) {
                editListener.correct(sumText);
            }

            return sourceText;
        }
    }

    public interface EditListener {
        void error(String msg);

        void correct(double value);
    }
}
