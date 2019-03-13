package com.example.common.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

@Data
public class BaseEntity {

    @JsonFormat(pattern = "yyyy-MM-hh HH:mm:ss")
    @JsonIgnore
    private Date createTime;

    @JsonIgnore
    private String createUser;

    @JsonFormat(pattern = "yyyy-MM-hh HH:mm:ss")
    private Date updateTime;

    @JsonIgnore
    private String updateUser;

    @JsonIgnore
    private boolean deleted;
}
