package com.project.teamttt.endpoint;

public class AuthEndPoint {
    public static final String AUTH_ROOT = "/auth";

    public static final String KAKAO_USER_CREATE = "/login/oauth2/code/kakao";

    public static final String NAVER_CREATE = AUTH_ROOT + "/naver";
    public static final String NAVER_CALLBACK = "/auth/naver/callback";

    public static final String GOOGLE_ROOT = "/google";
    public static final String GOOGLE_USER_CREATE = "/google/oauth2/callback";

    public static final String MEMBER_ROOT = AUTH_ROOT + "/member";
    public static final String MEMBER_SIGNUP = MEMBER_ROOT + "/signup";
    public static final String MEMBER_LOGIN = MEMBER_ROOT + "/login";

}
