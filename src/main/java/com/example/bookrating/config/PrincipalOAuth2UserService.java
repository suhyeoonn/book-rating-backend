package com.example.bookrating.config;

import com.example.bookrating.config.provider.GoogleUserInfo;
import com.example.bookrating.config.provider.OAuth2UserInfo;
import com.example.bookrating.entity.Member;
import com.example.bookrating.repository.MemberRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class PrincipalOAuth2UserService extends DefaultOAuth2UserService {

//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        OAuth2UserInfo oAuth2UserInfo = null;

        if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        }
        String provider = oAuth2UserInfo.getProvider();
        String providerId = oAuth2UserInfo.getProviderId();
        String username = provider+'_'+providerId;
        String email = oAuth2UserInfo.getEmail();
        String password = "oauth"; //bCryptPasswordEncoder.encode("oauth");

        Member member = memberRepository.findByUsername(username).orElse(null);

        if (member == null) {
             member = Member.builder()
                     .username(username)
                     .password(password)
                     .provider(provider)
                     .providerId(providerId)
                     .email(email)
                     .build();

            memberRepository.save(member);
        }

        // 시큐리티 세션 정보로 들어감
        return new PrincipleDetails(member, oAuth2User.getAttributes());
    }
}
