package com.generonumero.blocodaguarda.alert.event;


public class CountDownFinished {
    private int time;

    public CountDownFinished(int time) {
        this.time = time;
    }

    public int getTime() {
        return time;
    }
}
