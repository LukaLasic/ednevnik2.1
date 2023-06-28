package net.fgrprojekt;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;


@Controller
public class AppController {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private UserService service;
	@Autowired
	private RoleRepository roleRepo;
	@Autowired
	private UserRepository userRepo;
	
	@GetMapping("")
	public String viewHomePage() {
		return "index";
	}
	
	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new User());
		
		return "signup_form";
	}
	
	@PostMapping("/process_register")
	public String processRegister(User user) {
		service.registerDefaultUser(user);
		
		return "register_success";
	}
	
	@GetMapping("/users")
	public String listUsers(Model model) {
		List<User> listUsers = service.listAll();
		model.addAttribute("listUsers", listUsers);
		
		return "users";
	}

	@GetMapping("/users/edit/{id}")
	public String editUser(@PathVariable("id") Long id, Model model) {
		User user = service.get(id);

		List<Role> listRoles = service.listRoles();
		model.addAttribute("user", user);
		model.addAttribute("listRoles", listRoles);

		return "user_form";
	}
	private String decodePassword(User user) {
		String encodedPassword = user.getPassword();
		return passwordEncoder.matches(encodedPassword, encodedPassword) ? encodedPassword : null;
	}
	
	@PostMapping("/users/save")
	public String updateUserRole(@ModelAttribute("user") User user, Model model) {
		User currentUser = service.get(user.getId());
		currentUser.setRoles(user.getRoles());
		service.updateRole(	currentUser);
		return "redirect:/users";
	}

	@Autowired
	StudentRepository studentRepo;

	@GetMapping("/students")
	public String liststudents (Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		model.addAttribute("userDetails", userDetails);
		List<Student> liststudents = studentRepo.findAll();
		model.addAttribute("liststudents", liststudents);
		model.addAttribute("activeLink", "Knjige");
		return "students";
	}

	@GetMapping("students/add")
	public String showAddstudentForm (Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		model.addAttribute("activeLink", "Studenti");
		model.addAttribute("userDetails", userDetails);
		model.addAttribute("student", new Student());
		return "add_student";
	}

	@PostMapping("students/add")
	public String addstudent (@Valid Student student, BindingResult result, Model model) {
		boolean errors = result.hasErrors();
		if (errors) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			model.addAttribute("activeLink", "Studenti");
			model.addAttribute("userDetails", userDetails);
			model.addAttribute("student", student);
			return "add_student";
		}
		studentRepo.save(student);
		return "redirect:/students";
	}

	@GetMapping("students/edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
		Student student = studentRepo.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + id));
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		model.addAttribute("activeLink", "Student");
		model.addAttribute("userDetails", userDetails);
		model.addAttribute("student", student);
		return "edit_student";
	}

	@PostMapping("students/update/{student_id}")
	public String updateUser(@PathVariable("student_id") long id, @Valid Student student,
							 BindingResult result, Model model) {
		if (result.hasErrors()) {
			student.setStudent_id(id);
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			model.addAttribute("activeLink", "Studenti");
			model.addAttribute("userDetails", userDetails);
			model.addAttribute("student", student);
			return "edit_student";
		}

		studentRepo.save(student);
		return "redirect:/students";
	}

	@GetMapping("/student/delete/{id}")
	public String delete(@PathVariable("id") long id, Model model) {
		Student student = studentRepo.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + id));
		studentRepo.delete(student);
		return "redirect:/students";
	}

	@GetMapping("/prijava")
	public String showLoginForm(Model model){
		model.addAttribute("user", new User());
		return "login";
	}

	@GetMapping("/greska")
	public String showError(Model model){
		model.addAttribute("user", new User());
		return"greska";
	}

}
