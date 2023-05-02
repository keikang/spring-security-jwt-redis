package com.example.springsecurityjwtredis.model.reponse;

public interface RoleResConverter {

    Long getRoleId();

    String getRoleName();

    Integer getRoleCount();

    String getRoleDescription();

    String getDataAccessYn();
}
