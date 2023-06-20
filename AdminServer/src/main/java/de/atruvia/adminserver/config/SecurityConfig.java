package de.atruvia.adminserver.config;

import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableWebSecurity
public class SecurityConfig {

//    @Bean
//    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
//        UserDetails user = User.withUsername("user")
//                .password(passwordEncoder.encode("password"))
//                .roles("USER")
//                .build();
//
////        UserDetails admin = User.withUsername("admin")
////                .password(passwordEncoder.encode("geheim"))
////                .roles("USER", "ADMIN")
////                .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.authorizeRequests().anyRequest().permitAll();
//
//
//        return http.build();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//        return encoder;
//    }
}