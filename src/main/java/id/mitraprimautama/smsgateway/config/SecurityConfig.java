package id.mitraprimautama.smsgateway.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private static String SQL_LOGIN = "SELECT username, password, active as enabled from t_user where username=?";
  
  private static String SQL_PERMISSION = "SELECT u.username, r.nama as authority FROM t_user as u join t_user_role ur ON u.id = ur.id_user join t_roles r ON ur.id_role = r.id where u.username=?";

  @Autowired
  private DataSource ds;
  
  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    
    JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager();
    userDetailsManager.setDataSource(ds);

    PasswordEncoder encoder = new BCryptPasswordEncoder();
    
    auth
      // .userDetailsService(userDetailsService)
      // .inMemoryAuthentication()
      //   .withUser("emen").password(encoder.encode("12345")).roles("ADMIN")
      //   .and()
      //   .withUser("user").password(encoder.encode("12345")).roles("USER");
      .jdbcAuthentication()
      .dataSource(ds)
      .usersByUsernameQuery(SQL_LOGIN).passwordEncoder(encoder)
      .authoritiesByUsernameQuery(SQL_PERMISSION);
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
          .antMatchers("/bootstrap/**", "/css/**", "/images/**", "/font/**").permitAll()
          .anyRequest().authenticated()
          .and()
        .formLogin()
          .failureUrl("/login?error")
          .loginPage("/login")
          .defaultSuccessUrl("/dashboard", true)
          .permitAll()
          .and()
        .logout()
          .deleteCookies("JSESSIONID")
          .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
          .logoutSuccessUrl("/login")
          .permitAll();
  }

}