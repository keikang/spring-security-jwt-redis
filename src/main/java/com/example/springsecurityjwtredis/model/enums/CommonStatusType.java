package com.example.springsecurityjwtredis.model.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum CommonStatusType {
    SUCCESS(HttpStatus.OK, null, "OK")
    ,INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,null,"")
    ,BAD_REQUEST(HttpStatus.BAD_REQUEST, null, "")
    ,UNAUTHORIZED(HttpStatus.UNAUTHORIZED, null, "")

    ,JWS_EXPIRED(40050, BAD_REQUEST, "인증 세션이 만료되었습니다. 다시 인증을 시도해 주시기 바랍니다.")
    ,JWS_FORMAT_NOT_COMPATIBILITY(40051, BAD_REQUEST, "유효하지 않는 계정으로 접속하셨습니다. 다시 인증을 시도해 주시기 바랍니다.")
    ,JWS_MALFORMED(40052, BAD_REQUEST, "토큰구조가 올바르게 구성되지 않았습니다")
    ,JWS_INVALID_SIGNATURE(40053, BAD_REQUEST, "토큰의 서명이 올바르지 않습니다")

    ,USER_ALREADY_EXIST(40021, BAD_REQUEST, "이미 가입된 사용자입니다")
    ,USER_PHONE_ALREADY_EXIST(40022, BAD_REQUEST, "이미 존재하는 사용자 전화번호입니다")
    ,USER_NOT_FOUND(40023, BAD_REQUEST, "존재하지 않는 사용자입니다")
    ,USER_NOT_SELF_AUTHED(40024, BAD_REQUEST, "본인인증이 완료되지 않은 사용자입니다")
    ,USER_IS_DORMANT(40025, BAD_REQUEST, "휴면 유저로 분류된 사용자입니다")
    ,USER_NOT_CONNECTED_MYDATA_SERVICE(40026, BAD_REQUEST, "마이데이터 서비스를 연동하지 않은 사용자입니다")

    ,LOGIN_FAIL(42000, BAD_REQUEST, "아이디, 패스워드를 확인하세요")
    ;

    Object status;
    CommonStatusType parent;
    String msg;

    CommonStatusType(Object status, CommonStatusType parent, String msg) {
        this.status = status;
        this.parent = parent;
        this.msg = msg;
    }

    public int getDetailCd() {
        if(this.status instanceof HttpStatus) return ((HttpStatus) this.status).value();
        else return (Integer) status;
    }
}
