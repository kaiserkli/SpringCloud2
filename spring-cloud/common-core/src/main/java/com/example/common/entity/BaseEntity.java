package com.example.common.entity;

import lombok.Data;

import java.util.Date;

@Data
public class BaseEntity {
    private Date createTime;
    private String createUser;
    private Date updateTime;
    private String updateUser;
    private boolean deleted;
}
