package com.project.teamttt.endpoint;

public class AuthEndPoint {
    public static final String AUTH_ROOT = "/auth";

    public static final String KAKAO_CREATE = AUTH_ROOT + "/kakao";

    public static final String NAVER_CREATE = AUTH_ROOT + "/naver";
    public static final String NAVER_CALLBACK = "/auth/naver/callback";

    public static final String GOOGLE_CREATE = AUTH_ROOT + "/google";

    public static final String MEMBER_ROOT = AUTH_ROOT + "/member";
    public static final String MEMBER_SIGNUP = MEMBER_ROOT + "/signup";
    public static final String MEMBER_LOGIN = MEMBER_ROOT + "/login";

}
