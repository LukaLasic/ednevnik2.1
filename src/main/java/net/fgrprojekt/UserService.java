package net.fgrprojekt;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired RoleRepository roleRepo;
	
	@Autowired PasswordEncoder passwordEncoder;
	
	public void registerDefaultUser(User user) {
		Role roleUser = roleRepo.findByName("User");
		user.addRole(roleUser);
		encodePassword(user);
		userRepo.save(user);
	}
	
	public List<User> listAll() {
		return userRepo.findAll();
	}

	public User get(Long id) {
		return userRepo.findById(id).get();
	}


	
	public List<Role> listRoles() {
		return roleRepo.findAll();
	}
	
	public void save(User user) {
		encodePassword(user);
		userRepo.save(user);
	}
	public void updateRole(User user) {
		User existingUser = userRepo.findById(user.getId()).get();
		existingUser.setRoles(user.getRoles());
		userRepo.save(existingUser);
	}
	
	private void encodePassword(User user) {
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
	}
	private String decodePassword(User user) {
		String encodedPassword = user.getPassword();
		return passwordEncoder.matches(encodedPassword, encodedPassword) ? encodedPassword : null;
	}



}
