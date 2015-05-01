package com.ataulm.basic;

public class ItemInsetCalculator {

    private final int spanCount;
    private final int horizontalSpacingPx;

    public ItemInsetCalculator(int spanCount, int horizontalSpacingPx) {
        this.spanCount = spanCount;
        this.horizontalSpacingPx = horizontalSpacingPx;
    }

    public Calculator givenItemAtPosition(int position) {
        return new Calculator(position);
    }

    public static class Calculator {

        private final int position;

        public Calculator(int position) {
            this.position = position;
        }

        public int getLeftInset() {

            return 0;
        }

        public int getRightInset() {
            return 0;
        }

    }


}
