package com.example.common.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountInfo implements Serializable {
    /**
     * 账号ID
     */
    private Integer userInfoId;

    /**
     * 账号名称
     */
    private String username;

    /**
     * Access Token
     */
    private String accessToken;

    /**
     * Refresh Token
     */
    private String refreshToken;

}
