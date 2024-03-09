package kr.co.popin.infrastructure.config.security

import kr.co.popin.infrastructure.config.security.authorization.RequestMatchers
import kr.co.popin.infrastructure.config.security.entrypoint.AuthenticationEntryPoint
import kr.co.popin.infrastructure.config.security.filter.JwtExceptionFilter
import kr.co.popin.infrastructure.config.security.filter.JwtTokenFilter
import kr.co.popin.infrastructure.config.security.handler.AccessDeniedHandler
import kr.co.popin.infrastructure.config.security.service.UserDetailsService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig (
    private val userDetailsService: UserDetailsService,
    private val jwtTokenFilter: JwtTokenFilter,
    private val jwtExceptionFilter: JwtExceptionFilter,
    private val accessDeniedHandler: AccessDeniedHandler,
    private val authenticationEntryPoint: AuthenticationEntryPoint,
    private val securityRequestMatchers: RequestMatchers
) {
    @Bean
    fun securityFilterChain(
        http: HttpSecurity
    ): SecurityFilterChain = http
        .csrf { csrf -> csrf.disable() }
        .cors(Customizer.withDefaults())
        .httpBasic { httpBasic -> httpBasic.disable() }
        .formLogin { formLogin -> formLogin.disable() }
        .authorizeHttpRequests { request -> request
            .requestMatchers(*securityRequestMatchers.permitAll.toTypedArray()).permitAll()
            .anyRequest().authenticated()
        }
        .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
        .addFilterBefore(jwtExceptionFilter, JwtTokenFilter::class.java)
        .sessionManagement { sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
        .headers { headers ->
            headers.frameOptions { frameOptions -> frameOptions.disable() }
        }
        .userDetailsService(userDetailsService)
        .exceptionHandling { exceptionHandling ->
            exceptionHandling.accessDeniedHandler(accessDeniedHandler)
            exceptionHandling.authenticationEntryPoint(authenticationEntryPoint)
        }
        .build()
}