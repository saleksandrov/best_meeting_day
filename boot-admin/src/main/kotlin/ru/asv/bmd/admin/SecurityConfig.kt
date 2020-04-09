package ru.asv.bmd.admin

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler
import org.springframework.security.web.csrf.CookieCsrfTokenRepository


@Configuration
class SecurityConfig : WebSecurityConfigurerAdapter() {

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        val successHandler = SavedRequestAwareAuthenticationSuccessHandler()
        successHandler.setTargetUrlParameter("redirectTo")
        successHandler.setDefaultTargetUrl("/sba")
        http.csrf().disable()
                .authorizeRequests().antMatchers("/actuator/**").permitAll()

        http.authorizeRequests()
                .antMatchers("/sba/assets/**").permitAll()
                .antMatchers("/sba/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/sba/login").successHandler(successHandler).and()
                .logout().logoutUrl("/sba/logout").and()
                .httpBasic().and()
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .ignoringAntMatchers(
                        "/sba/instances",
                        "/sba/actuator/**"
                )
    }

}