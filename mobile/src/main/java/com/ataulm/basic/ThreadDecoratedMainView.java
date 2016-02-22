package com.ataulm.basic;

import android.os.Handler;
import android.os.Looper;

import com.ataulm.basic.model.line.Line;

import java.util.List;

public class ThreadDecoratedMainView implements MainView{

    private final Handler handler;
    private final MainView mainView;

    public ThreadDecoratedMainView(MainView mainView) {
        this.mainView = mainView;
        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void showLines(final List<Line> lines) {
        handler.post(
                new Runnable() {
                    @Override
                    public void run() {
                        mainView.showLines(lines);
                    }
                });
    }
}
