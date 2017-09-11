package com.example;

import android.app.Presentation;
import android.content.Context;
import android.media.MediaRouter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;

import java.util.ArrayList;
import java.util.List;

public class MyActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);


        MediaRouter mediaRouter = (MediaRouter) getSystemService(MEDIA_ROUTER_SERVICE);
        mediaRouter.addCallback(MediaRouter.ROUTE_TYPE_LIVE_VIDEO, new MediaRouter.SimpleCallback() {
            @Override
            public void onRoutePresentationDisplayChanged(MediaRouter router, MediaRouter.RouteInfo info) {
                Display display = foo(info);
                if (display == null) {
                    Log.d("!!! ", "no display");

                    // TODO: stop showing
                } else {
                    Log.d("!!! ", display.getName());
                    new TicTacToePresentation(MyActivity.this, display).show();
                }
            }

            @Nullable
            private Display foo(@Nullable MediaRouter.RouteInfo info) {
                return info == null ? null : info.getPresentationDisplay();
            }
        });
    }

    private static class TicTacToePresentation extends Presentation {

        public TicTacToePresentation(Context outerContext, Display display) {
            super(outerContext, display);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.presentation_tic_tac_toe);

            Board board = new Board.Builder().set(0, Value.NAUGHT).build();
            ((TicTacToeView) findViewById(R.id.presentation_tic_tac_toe)).display(board);
        }
    }

    /**
     * 012,
     * 345,
     * 678
     */
    static class Board {

        static final int POSITIONS = 9;

        private final List<Value> values;

        Board(List<Value> values) {
            this.values = values;
        }

        Value get(int i) {
            return values.get(i);
        }

        static class Builder {

            private final List<Value> values = new ArrayList<>(POSITIONS);

            Builder() {
                for (int i = 0; i < POSITIONS; i++) {
                    this.values.add(Value.EMPTY);
                }
            }

            Builder set(int position, Value value) {
                if (position < 0 || position > POSITIONS - 1) {
                    throw new IndexOutOfBoundsException("position must be 0-8, you gave: " + position);
                }

                values.set(position, value);
                return this;
            }

            Board build() {
                return new Board(values);
            }
        }
    }

    enum Value {

        EMPTY,
        NAUGHT,
        CROSS
    }
}
