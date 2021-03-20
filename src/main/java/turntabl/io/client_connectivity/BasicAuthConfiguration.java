//package turntabl.io.client_connectivity;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//
//@Configuration
//@EnableWebSecurity
//
//public class BasicAuthConfiguration extends WebSecurityConfigurerAdapter {
//
//        @Override
//        protected void configure(HttpSecurity http)
//                throws Exception {
//                http.csrf().disable()
//                        .authorizeRequests(authorizeRequests ->
//                                authorizeRequests
//                                        .antMatchers("/api/user/*").hasAnyRole("user", "admin")
//                                        .antMatchers("/api/admin/*", "/api/*").hasRole("admin")
//                                        .antMatchers("/login", "/register").permitAll()
//                        )
//                        .httpBasic().realmName("My org ream")
//                        .and()
//                        .sessionManagement()
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        }
//}
//
