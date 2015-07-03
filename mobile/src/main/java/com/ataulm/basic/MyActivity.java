package com.ataulm.basic;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MyActivity extends Activity {

    private BreakfastBarLayout breakfastBarLayout;
    private Toast toast;
    private int count;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        final View itemOne = findViewById(R.id.item_1);
        final View itemTwo = findViewById(R.id.item_2);
        final View itemThree = findViewById(R.id.item_3);

        View itemReset = findViewById(R.id.reset);
        itemReset.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemOne.setVisibility(View.VISIBLE);
                        itemTwo.setVisibility(View.VISIBLE);
                        itemThree.setVisibility(View.VISIBLE);
                    }
                }
        );

        itemOne.setOnClickListener(hide(itemOne, "Item 1 hidden", "Item 1 restored!"));
        itemTwo.setOnClickListener(hide(itemTwo, "Item 2 hidden", "Item 2 restored!"));
        itemThree.setOnClickListener(hide(itemThree, "Item 3 hidden", "Item 3 restored!"));

        breakfastBarLayout = (BreakfastBarLayout) findViewById(R.id.breakfast_bar);
    }

    private View.OnClickListener hide(final View item, final String messageWhenHiding, final String messageWhenRestoring) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setVisibility(View.GONE);
                showBreakfastBar(messageWhenHiding, undoHide(item, messageWhenRestoring));
            }
        };
    }

    private View.OnClickListener undoHide(final View item, final String message) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast(message);
                item.setVisibility(View.VISIBLE);
                breakfastBarLayout.setVisibility(View.GONE);
            }
        };
    }

    private void showBreakfastBar(String message, View.OnClickListener undoClickListener) {
        breakfastBarLayout.setMessage(message);
        breakfastBarLayout.setAction("UNDO", undoClickListener);
        breakfastBarLayout.setVisibility(View.VISIBLE);
        breakfastBarLayout.requestFocus();
    }

    private void toast(String message) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(MyActivity.this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

}