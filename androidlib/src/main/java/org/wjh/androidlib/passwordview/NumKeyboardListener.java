package org.wjh.androidlib.passwordview;

public interface NumKeyboardListener {

    void addByIndex(int index);

    void deleteByIndex(int index);

    void complete(String password);
}
