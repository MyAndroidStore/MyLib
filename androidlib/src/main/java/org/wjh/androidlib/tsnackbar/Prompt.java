package org.wjh.androidlib.tsnackbar;

import org.wjh.androidlib.R;

public enum Prompt {
    /**
     * 红色,错误
     */
    ERROR(R.color.prompt_error),

    /**
     * 橘黄色,警告
     */
    WARNING(R.color.prompt_warning),

    /**
     * 绿色,成功
     */
    SUCCESS(R.color.prompt_success);


    private int backgroundColor;

    Prompt(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
