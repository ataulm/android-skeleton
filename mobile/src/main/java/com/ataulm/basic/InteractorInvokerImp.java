package com.ataulm.basic;

import java.util.concurrent.Executors;

public class InteractorInvokerImp implements InteractorInvoker{
    @Override
    public void execute(Runnable runnable) {
        Executors.newSingleThreadExecutor().execute(runnable);
    }
}
