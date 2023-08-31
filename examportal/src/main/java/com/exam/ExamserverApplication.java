package com.exam;

import com.exam.models.Role;
import com.exam.models.User;
import com.exam.models.UserRole;
import com.exam.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class ExamserverApplication   implements CommandLineRunner  {
	@Autowired
   private UserService userService;
	public static void main(String[] args) {


		SpringApplication.run(ExamserverApplication.class, args);


	}


	@Override
	public void run(String... args) throws Exception {
		System.out.println("starting code");
//		User user=new User();
//		user.setUserName("akash22");
//		user.setFirstName("Akash");
//		user.setLastName("Yadav");
//		user.setEmail("abaa@giaml.com" );
//		user.setProfile("janljdn.png");
//		user.setPhone("9967630255");
//		Role role=new Role();
//		role.setRoleId(22L);
//		role.setRoleName("admin");
//		Set<UserRole> userRoleSet=new HashSet<>();
//		  UserRole urole=new UserRole();
//		  urole.setRole(role);
//		  urole.setUser(user);
//		userRoleSet.add(urole);
//
//		 User user1=this.userService.createUser(user,userRoleSet);
//		System.out.println(user1.getUserName());

	}
}
