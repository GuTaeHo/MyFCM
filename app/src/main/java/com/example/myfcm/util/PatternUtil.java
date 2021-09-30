package com.example.myfcm.util;

import java.util.regex.Pattern;

public class PatternUtil {
    // 이메일 패턴
    private static final Pattern emailPattern = Pattern.compile("^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$");
    // 닉네임 패턴 (영어, 한국어, 숫자, 최소 5자 최대 20자까지 허용)
    private static final Pattern nickPattern = Pattern.compile("^[가-힣ㄱ-ㅎa-zA-Z0-9._-]{5,20}$");
    // 비밀번호 패턴 (//'숫자', '문자', '특수문자' 무조건 1개 이상, 비밀번호 '최소 8자에서 최대 20자'까지 허용)
    private static final Pattern passwordPattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,20}$");

    public static boolean isEmailPattern(String email) {
        return emailPattern.matcher(email).find();
    }
    public static boolean isNickNamePattern(String nickName) {
        return nickPattern.matcher(nickName).find();
    }
    public static boolean isPassWordPattern(String password) {
        return passwordPattern.matcher(password).find();
    }
}
