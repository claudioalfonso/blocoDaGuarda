package com.generonumero.blocodaguarda.login.event;


public class LoginFailed {

    private boolean isCanceled;

    public LoginFailed(boolean isCanceled) {
        this.isCanceled = isCanceled;
    }

    public boolean isCanceled() {
        return isCanceled;
    }
}
