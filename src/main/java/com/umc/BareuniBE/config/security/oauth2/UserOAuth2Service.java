package com.umc.BareuniBE.config.security.oauth2;

import com.umc.BareuniBE.dto.UserReq;
import com.umc.BareuniBE.dto.UserRes;
import com.umc.BareuniBE.entities.User;
import com.umc.BareuniBE.repository.UserRepository;
import com.umc.BareuniBE.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthorizationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserOAuth2Service extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthorizationException{
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        Map<String, Object> kakao_account = (Map<String, Object>) attributes.get("kakao_account");
        String email = (String) kakao_account.get("email");

        /*Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
        String nickname = (String) properties.get("nickname");*/

        /*회원가입 시키기*/
        User newUser = new User();
        newUser.setEmail(email);
        User user = userRepository.saveAndFlush(newUser);

        DefaultOAuth2User dO = new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("ROLE_MEMBER")), attributes, "id");
        log.info(user.getEmail());
        log.info(dO.toString());
        return dO;
    }
}
