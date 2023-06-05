package com.nothing.justdoit.config.auth;


import com.nothing.justdoit.domain.user.Role;
import org.springframework.context.annotation.Bean;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;



@EnableWebSecurity  //설정 활성화
@Configuration
public class SecurityConfig{

    private final CustomOAuth2UserService customOAuth2UserService;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService) {
        this.customOAuth2UserService = customOAuth2UserService;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //h2-console 화면을 사용하기 위해 해당 옵션들 비활성화
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/", "/css/**", "/images/**", "/js/**", "/profile").permitAll()//모두허용
                        .requestMatchers("/api/v1/**").hasRole(Role.GUEST.name())//USER만 허용
                        .anyRequest()
                        .authenticated())
                .oauth2Login(oauthLogin -> oauthLogin.
                        userInfoEndpoint(userInfo-> userInfo.
                                userService(customOAuth2UserService)))
                       //로그인 성공 후속 조치를 진행할 인터페이스 구현체 등록
                .logout((logout) -> logout.logoutUrl("/logout").logoutSuccessUrl("/"));//로그아웃 성공시 이동경로 지정

        return http.build();
    }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers("/h2-console/**");
    }
}
