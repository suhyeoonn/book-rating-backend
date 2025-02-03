package com.example.bookrating.config.provider;

import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(String provider, OAuth2User oAuth2User) {
        switch (provider) {
            case "google":
                return new GoogleUserInfo(oAuth2User.getAttributes());
            case "naver":
                return new NaverUserInfo((Map) oAuth2User.getAttributes().get("response"));

            default:
                throw new IllegalArgumentException("Unsupported provider: " + provider);
        }
    }
}
