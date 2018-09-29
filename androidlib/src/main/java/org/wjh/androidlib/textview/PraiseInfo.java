package org.wjh.androidlib.textview;

/**
 * 人名的实体类
 */
public class PraiseInfo {

    // 标识
    private String mark;
    private String nickname;

    public PraiseInfo(String nickname) {
        this.nickname = nickname;
    }

    public PraiseInfo(String mark, String nickname) {
        this.mark = mark;
        this.nickname = nickname;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
