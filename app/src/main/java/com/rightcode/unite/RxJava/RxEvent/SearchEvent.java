package com.rightcode.unite.RxJava.RxEvent;

import com.rightcode.unite.Fragment.BottomFragment;

import lombok.Getter;

@Getter
public class SearchEvent {

    private String search;
    private BottomFragment.Menu menu;

    public SearchEvent(BottomFragment.Menu menu, String search) {
        this.menu = menu;
        this.search = search;
    }
}
