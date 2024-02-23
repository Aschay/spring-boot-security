## spring-boot-security

Step by step implementing   spring security
## 1. Using default spring security configuration 
At the start of adding spring security to application, it will create a username with value user and encrypted password that will print it out in console .

As result it  authenticate any request with it "basic" authentication.

Also by default its creates a "loginform" with those credientials .

## 2.Configure filters and in-memory Authentication in spring security
### allowing all requests to the url "/"

```java
@Bean
WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers("/");
}
```
###  allowing requests to specific urls for specifics users

```java
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
```java
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

## 3.Configuring JDBC based Authentications
Spring Security  provides support for username-and-password-based authentication that is retrieved by using JDBC. 

The used example is MySQL as db, we populated users(admin , user) with password respectively admin ,user encrypted by Bcrypt with 10 rounds and given roles admin, user and persisted its in the databse 
Then to authenticate from the db 
```java
@Autowired
public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
	PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
	auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder);
}
````

## 4. Configuring AuthenticationProvider 

for further customization we can also  set up an Authentication Provider in Spring Security, allowing for additional flexibility compared to the standard scenario using a simple UserDetailsService and UserDetails ;

```java
@Bean
public UserDetailsService userDetailsService() {
	return new UserDetailsServiceCustom();
	}

@Bean
public PasswordEncoder passwordEncoder() {
	return new BCryptPasswordEncoder(10);
	}

@Bean
public AuthenticationProvider authenticationProvider() {
	DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
	authenticationProvider.setUserDetailsService(userDetailsService());
	authenticationProvider.setPasswordEncoder(passwordEncoder());
	return authenticationProvider;
}

@Bean
public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
	return config.getAuthenticationManager();
}
```
## 5. Configuring jwt for authentication 
We can further use jwt for authentication  with first implementing jwtprovider to validate/generate tokens and  it respective filter 
At controller level we can just use  for example
```java
@GetMapping(value = "/admin")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	HashMap<String, String> welcomeMessageAdmin() {
		HashMap<String, String> map = new HashMap<>();
		map.put("message", "Hello Admin");
		map.put("Note", "This page is only authorized to admin");
		return map;
}
```
