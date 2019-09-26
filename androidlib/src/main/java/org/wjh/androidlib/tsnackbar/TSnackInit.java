package org.wjh.androidlib.tsnackbar;

public class TSnackInit {

    private static int mActionBarHight;

    private static TSnackInit instance;

    private TSnackInit() {

        if (mActionBarHight == 0)
            throw new RuntimeException("Please invoke TSnackInit.init(actionBarHight) on Application#onCreate()");
    }

    public static TSnackInit getInstance() {
        if (instance == null)
            synchronized (TSnackInit.class) {
                if (instance == null)
                    instance = new TSnackInit();
            }
        return instance;
    }

    // 需要在Application进行全局初始化
    public static void init(int actionBarHight) {
        mActionBarHight = actionBarHight;
    }

    public int getActionBarHight() {
        return mActionBarHight;
    }
}
