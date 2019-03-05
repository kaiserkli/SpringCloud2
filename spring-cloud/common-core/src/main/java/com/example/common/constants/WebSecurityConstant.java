package com.example.common.constants;

public interface WebSecurityConstant {
	
	//Token signing key
	public static final String TOKEN_SECRET_KEY = "1234567890";
	
	//Access Token expire time (s)
	public static final long ACCESS_TOKEN_EXPIRATION = 6000;

	//Refresh Token expire time (d)
	public static final long REFRESH_TOKEN_EXPIRATION = 7;
}
