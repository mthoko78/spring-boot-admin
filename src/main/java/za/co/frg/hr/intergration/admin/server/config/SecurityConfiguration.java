package za.co.frg.hr.intergration.admin.server.config;

import de.codecentric.boot.admin.server.config.AdminServerProperties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.UUID;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final AdminServerProperties adminServer;

    public SecurityConfiguration(AdminServerProperties adminServer) {
        this.adminServer = adminServer;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("redirectTo");
        successHandler.setDefaultTargetUrl(this.adminServer.getContextPath() + "/");

        http.authorizeHttpRequests(req -> req.requestMatchers(new AntPathRequestMatcher(this.adminServer.getContextPath() + "/assets/**"))
                .permitAll()
                .requestMatchers(
                    new AntPathRequestMatcher(this.adminServer.getContextPath() + "/login"),
                    new AntPathRequestMatcher(this.adminServer.getContextPath() + "/actuator/**"))
                .permitAll()
                .anyRequest()
                .authenticated())
            .formLogin(formLogin -> formLogin.loginPage(this.adminServer.getContextPath() + "/login")
                .successHandler(successHandler))
            .logout((logout) -> logout.logoutUrl(this.adminServer.getContextPath() + "/logout"))
            .httpBasic(Customizer.withDefaults())
            .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .ignoringRequestMatchers(
                    new AntPathRequestMatcher(this.adminServer.getContextPath() + "/instances", HttpMethod.POST.toString()),
                    new AntPathRequestMatcher(this.adminServer.getContextPath() + "/instances/*", HttpMethod.DELETE.toString()),
                    new AntPathRequestMatcher(this.adminServer.getContextPath() + "/actuator/**")));
        return http.build();
    }
}