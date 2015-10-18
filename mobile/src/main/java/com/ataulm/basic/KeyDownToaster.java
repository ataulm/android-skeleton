package com.ataulm.basic;

import android.content.Context;
import android.view.KeyEvent;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class KeyDownToaster implements KeyEvent.Callback {

    private static final List<String> DOTS = Arrays.asList(",    ", ",~   ", ",~*  ", ",~*~ ", ",~*~,", " ~*~,", "  *~,", "   ~,", "    ,");

    private static Iterator<String> dotsIterator = DOTS.iterator();

    private final Context context;

    public KeyDownToaster(Context context) {
        this.context = context;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (!dotsIterator.hasNext()) {
            dotsIterator = DOTS.iterator();
        }
        Toaster.display(context, dotsIterator.next() + " | " + keyPressed(keyCode));
        return false;
    }

    private String keyPressed(int keyCode) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_NUMPAD_ENTER:
            case KeyEvent.KEYCODE_ENTER:
            case KeyEvent.KEYCODE_DPAD_CENTER:
                return "select";
            case KeyEvent.KEYCODE_DPAD_LEFT:
                return "left";
            case KeyEvent.KEYCODE_DPAD_UP:
                return "up";
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                return "right";
            case KeyEvent.KEYCODE_DPAD_DOWN:
                return "down";
            default:
                return "dunno";
        }
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public boolean onKeyMultiple(int keyCode, int count, KeyEvent event) {
        return false;
    }

}
