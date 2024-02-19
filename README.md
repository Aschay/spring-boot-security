## spring-boot-security

Step by step implementing   spring security
## 1. Using default spring security configuration 
At the start of adding spring security to application, it will create a username with value user and encrypted password that will print it out in console .

As result it  authenticate any request with it "basic" authentication.

Also by default its creates a "loginform" with those credientials .

## 2.customize some features in spring security
### allowing all requests to the url "/"

```
@Bean
WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers("/");
}
```
###  allowing request to specific url for specifics users

```	
@Bean
SecurityFilterChain web(HttpSecurity http) throws Exception {
	http
        .authorizeHttpRequests(authorize -> authorize
						                    .requestMatchers("/user").hasRole("USER")       
						                     .requestMatchers("/admin").hasRole("ADMIN")     
						                      .anyRequest().authenticated()                      
					          )
					        //.formLogin(withDefaults()); //login form
					        .httpBasic(withDefaults()); // http basic in in header of the http request
	return http.build();
}

```
### create in-memory users and authenticate them
 ```
@Bean
public static PasswordEncoder encoder() {
	return new BCryptPasswordEncoder(10);
}
	
@Bean
public InMemoryUserDetailsManager userDetailsService() {
	String passwordUser="user";
	String hashedPasswordUser=encoder().encode(passwordUser);
	UserDetails user = User.builder()
		.username("user")
		.password(hashedPasswordUser)
		.roles("USER")
		.build();
	String passwordAdmin="admin";
	String hashedPasswordAdmin=encoder().encode(passwordAdmin);
	UserDetails admin =  User.builder()
			.username("admin")
			.password(hashedPasswordAdmin)
			.roles("USER", "ADMIN")
			.build();
	return new InMemoryUserDetailsManager(user, admin);
}
```
Full example at branch v1

