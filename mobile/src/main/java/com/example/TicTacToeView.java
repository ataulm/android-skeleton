package com.example;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TicTacToeView extends LinearLayout {

    private List<View> positions = new ArrayList<>(MyActivity.Board.POSITIONS);

    public TicTacToeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        super.setOrientation(VERTICAL);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View.inflate(getContext(), R.layout.merge_tic_tac_toe, this);
        positions.add(findViewById(R.id.position_0));
        positions.add(findViewById(R.id.position_1));
        positions.add(findViewById(R.id.position_2));
        positions.add(findViewById(R.id.position_3));
        positions.add(findViewById(R.id.position_4));
        positions.add(findViewById(R.id.position_5));
        positions.add(findViewById(R.id.position_6));
        positions.add(findViewById(R.id.position_7));
        positions.add(findViewById(R.id.position_8));
    }

    public void display(MyActivity.Board board) {
        for (int i = 0; i < positions.size(); i++) {
            MyActivity.Value value = board.get(i);
            TextView textView = (TextView) positions.get(i);
            String displayTextForValue = getDisplayTextFor(value);
            textView.setText(displayTextForValue);
        }
    }

    private String getDisplayTextFor(MyActivity.Value value) {
        switch (value) {
            case EMPTY:
                return "";
            case NAUGHT:
                return "o";
            case CROSS:
                return "x";
            default:
                throw new IllegalStateException("unknown value: " + value);
        }
    }
}
