package com.rightcode.unite.RxJava.RxEvent;

import lombok.Getter;

@Getter
public class LoginEvent {

    private Boolean isLogin;

    public LoginEvent(Boolean isLogin) {
        this.isLogin = isLogin;
    }
}
