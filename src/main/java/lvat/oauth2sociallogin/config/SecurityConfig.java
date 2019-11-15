package lvat.oauth2sociallogin.config;

import lvat.oauth2sociallogin.security.CustomUserDetailService;
import lvat.oauth2sociallogin.security.RestAuthenticationEntryPoint;
import lvat.oauth2sociallogin.security.TokenAuthenticationFilter;
import lvat.oauth2sociallogin.security.oauth2.CustomOAuth2UserService;
import lvat.oauth2sociallogin.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import lvat.oauth2sociallogin.security.oauth2.OAuth2AuthenticationFailureHandler;
import lvat.oauth2sociallogin.security.oauth2.OAuth2AuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomUserDetailService customUserDetailService;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    @Autowired
    public SecurityConfig(CustomUserDetailService customUserDetailService,
                          CustomOAuth2UserService customOAuth2UserService,
                          OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler,
                          OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler,
                          HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository) {
        this.customUserDetailService = customUserDetailService;
        this.customOAuth2UserService = customOAuth2UserService;
        this.oAuth2AuthenticationSuccessHandler = oAuth2AuthenticationSuccessHandler;
        this.oAuth2AuthenticationFailureHandler = oAuth2AuthenticationFailureHandler;
        this.httpCookieOAuth2AuthorizationRequestRepository = httpCookieOAuth2AuthorizationRequestRepository;
    }

    @Bean
    public HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2UserInfoAuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean(value = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.customUserDetailService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.csrf().disable();
        http.formLogin().disable();
        http.httpBasic().disable();
        http.exceptionHandling().authenticationEntryPoint(new RestAuthenticationEntryPoint());
        http
                .authorizeRequests()
                .antMatchers("/",
                        "/error",
                        "/favicon.ico",
                        "/**/*.png",
                        "/**/*.gif",
                        "/**/*.svg",
                        "/**/*.jpg",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js")
                .permitAll();
        http
                .authorizeRequests()
                .antMatchers("/auth/**", "/oauth2/**")
                .permitAll();
        http
                .authorizeRequests()
                .anyRequest()
                .authenticated();
        //0
        http
                //1
                .oauth2Login()
                //2
                .authorizationEndpoint()
                //3
                .baseUri("/oauth2/authorize")
                //3
                .authorizationRequestRepository(httpCookieOAuth2UserInfoAuthorizationRequestRepository())
                //3
                .and()
                //2
                .redirectionEndpoint()
                //3
                .baseUri("/oauth2/callback/*")
                //3
                .and()
                //2
                .userInfoEndpoint()
                //3
                .userService(customOAuth2UserService)
                //3
                .and()
                //2
                .successHandler(oAuth2AuthenticationSuccessHandler)
                //2
                .failureHandler(oAuth2AuthenticationFailureHandler);
        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
