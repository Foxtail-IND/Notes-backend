package com.foxtail.NotesBackend.services;

import com.foxtail.NotesBackend.models.Users;
import com.foxtail.NotesBackend.repositories.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest , OAuth2User> {


    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest); // ✅ Initialize properly

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String picture = oAuth2User.getAttribute("picture");
        String providerId = oAuth2User.getAttribute("sub");  // Google’s unique ID

        if (email == null) {
            throw new OAuth2AuthenticationException("Email not found from OAuth provider");
        }

        // ✅ Check if user exists
        Users user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            // ✅ Save new user
            user = new Users();
            user.setEmail(email);
            user.setName(name);
            user.setImageUrl(picture);
            user.setProvider("GOOGLE");
            user.setProviderId(providerId);
            userRepository.save(user);
        }

        return new DefaultOAuth2User(
                Collections.singleton(new OAuth2UserAuthority(oAuth2User.getAttributes())),
                oAuth2User.getAttributes(),
                "email" // ✅ This should be the primary key attribute (email)
        );
    }
}
