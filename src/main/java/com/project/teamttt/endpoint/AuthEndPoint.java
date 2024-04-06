package com.project.teamttt.endpoint;

public class AuthEndPoint {
    public static final String KAKAO_CALLBACK = "/login/oauth2/code/kakao";

    public static final String NAVER_CREATE_URI = "/auth/naver";
    public static final String NAVER_CALLBACK = "/auth/naver/callback";

    public static final String GOOGLE_CREATE_URI = "/google";
    public static final String GOOGLE_CALLBACK = "/google/oauth2/callback";

    public static final String MEMBER_SIGNUP = "/auth/member/signup";
    public static final String MEMBER_LOGIN = "/auth/member/login";
    public static final String MEMBER_FIND_PASSWORD = "/auth/member/findPassword";

    public static final String MEMBER_UPDATE_PASSWORD = "/auth/member/updatePassword";

}
