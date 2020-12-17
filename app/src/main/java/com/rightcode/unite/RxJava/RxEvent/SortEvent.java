package com.rightcode.unite.RxJava.RxEvent;

import lombok.Getter;

@Getter
public class SortEvent {

    private String sort;

    public SortEvent(String sort) {
        this.sort = sort;
    }
}
