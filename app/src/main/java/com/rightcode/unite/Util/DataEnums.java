package com.rightcode.unite.Util;

import com.bluelinelabs.logansquare.LoganSquare;
import com.rightcode.unite.network.typeConverter.ProviderConverter;
import com.rightcode.unite.network.typeConverter.RoleConverter;

public class DataEnums {

    public void registerTypeConverter() {
        LoganSquare.registerTypeConverter(RoleDiff.class, new RoleConverter());
        LoganSquare.registerTypeConverter(Provider.class, new ProviderConverter());
    }

    public enum DiffType {
        JOIN("join"),
        FIND("find"),
        UPDATE("update");

        private String type;

        DiffType(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return type;
        }

        public static DiffType getEnum(String value) {
            for (DiffType resultCode : values()) {
                if (resultCode.toString().equalsIgnoreCase(value)) {
                    return resultCode;
                }
            }
            return null;
        }

        public boolean equals(DiffType code) {
            return type != null ? type.equals(code.toString()) : false;
        }
    }

    public enum Provider {
        kakao,
        naver,
        facebook,
        google;


        public static Provider getEnum(String value) {
            for (Provider resultCode : values()) {
                if (resultCode.toString().equalsIgnoreCase(value)) {
                    return resultCode;
                }
            }
            return null;
        }
    }


    public enum SMSDiff {
        join, // - 회원가입시
        find, // - 아이디 찾기 및 비밀번호 변경
        update; // - 회원정보 수정


        public static SMSDiff getEnum(String value) {
            for (SMSDiff resultCode : values()) {
                if (resultCode.toString().equalsIgnoreCase(value)) {
                    return resultCode;
                }
            }
            return null;
        }
    }

    public enum RoleDiff {
        Student("student"), // - 학생
        Buddy("buddy"); // - 선생님

        private String role;

        RoleDiff(String role) {
            this.role = role;
        }

        @Override
        public String toString() {
            return role;
        }

        public static RoleDiff getEnum(String value) {
            for (RoleDiff resultCode : values()) {
                if (resultCode.toString().equalsIgnoreCase(value)) {
                    return resultCode;
                }
            }
            return Student;
        }
    }
}
