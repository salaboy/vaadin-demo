package com.example.demo.config;

import com.example.demo.products.service.SessionsService;
import com.example.demo.ui.LoginView;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.server.HandlerHelper;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSessionListener;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;
//import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableWebSecurity
//@EnableRedisHttpSession
@Configuration
class VaadinSecurityConfig extends VaadinWebSecurity {


  @Bean
  public ServletListenerRegistrationBean<HttpSessionListener> sessionListener(SessionsService sessionsService) {
    ServletListenerRegistrationBean<HttpSessionListener> listenerRegBean =
            new ServletListenerRegistrationBean<>();

    listenerRegBean.setListener(new MyHttpSessionListener(sessionsService));
    return listenerRegBean;
  }


  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // Vaadin has its own CSRF protection.
    // Spring CSRF is not compatible with Vaadin internal requests
    http.csrf(cfg -> cfg.ignoringRequestMatchers(this::ignoreFrameworkInternalRequests));

    http.authorizeHttpRequests(requests -> {

      // Permit access to actuator endpoint (running on another port anyway)

      requests.requestMatchers(this::ignoreFrameworkInternalRequests).permitAll();
      // Permit access to /login**
      requests.requestMatchers(AntPathRequestMatcher.antMatcher("/login" + "**")).permitAll();
      // Allow access to static resources ("/VAADIN/build/**" or "/VAADIN/**" (locally in DevMode)) as well as
      // to PUSH and dynamic generated content
      requests.requestMatchers(AntPathRequestMatcher.antMatcher("/VAADIN/**")).permitAll();
      // Secure everything else
    });
    http.formLogin(login -> {
      login.permitAll()
              .loginPage("/login");
    });

    http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(withDefaults());
    super.configure(http);

    setLoginView(http, LoginView.class, "/logged-out.html");
  }

  private boolean ignoreFrameworkInternalRequests(HttpServletRequest request) {
    // Permit access to vaadin's internal communication - /* means the vaadin url-mapping; not the context path
    return HandlerHelper.isFrameworkInternalRequest("/*", request);
  }

  @Bean
  public UserDetailsManager userDetailsManager() {
    LoggerFactory.getLogger(VaadinSecurityConfig.class)
            .warn("NOT FOR PRODUCTION: Using in-memory user details manager!");
    var user = User.withUsername("user")
            .password("{noop}password")
            .roles("USER")
            .build();
    var admin = User.withUsername("admin")
            .password("{noop}password")
            .roles("ADMIN")
            .build();
    return new InMemoryUserDetailsManager(user, admin);
  }

  @Bean
  public ObjectMapper mapper() {
    return new ObjectMapper();
  }
}