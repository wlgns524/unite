package com.rightcode.unite.network.socket.model;

import androidx.annotation.NonNull;

import lombok.Getter;

@Getter
public class ChatData {

    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    private Long id; // 동행 고유키
    private String message; // 메시지
    private String type; // 메시지 타입 ('system', 'message')
    private Long userId; // 보낸 유저 고유키
    private String name; // 보낸 유저 닉네임
    private String image; // 보낸 유저 프로필 이미지
    private String createdAt; // 최근 메시지 보낸 시간
    private Boolean isLeader; // 채팅보낸 유저 방장 여부

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // life cycle
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // override
    //----------------------------------------------------------------------------------------------

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }

    //----------------------------------------------------------------------------------------------
    // public
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // protected
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // private
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // inner class
    //----------------------------------------------------------------------------------------------
}
