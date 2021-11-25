package comp.A.project.controllers;

import comp.A.project.DAO.UserEntity;
import comp.A.project.forms.UserForm;
import comp.A.project.services.User.BookQueryService;
import comp.A.project.services.User.UserQueryService;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

/*  HomeController

    Handles the following routes:
        GET
            /
            /index
            /login
            /register
            /admin
*/
@Controller
public class HomeController {
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private UserQueryService userQueryService;
    @Autowired
    private BookQueryService bookQueryService;

    @GetMapping({"/", "/index"})
    public String getHome(Model model, Principal principal) {
        log.info("Request: index");

        // Is user authenticated?
        if (principal != null) {
            try {
                // Get user data
                UserEntity user = userQueryService.getByUsername(principal.getName());
                model.addAttribute("user", user);
                // Could add more cases as more user types are created if there is time
                if ("ADMIN".equals(user.getAuthority())) {
                    return getAdmin(model);
                }
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        }
        return "home";
    }

    @GetMapping("/login")
    public String getLogin(Model model) {
        log.info("Request: login");

        return "login";
    }

    @GetMapping("/register")
    public String getRegister(@ModelAttribute UserForm userForm) {
        log.info("Request: register");

        return "register";
    }

    @GetMapping("/admin")
    public String getAdmin(Model model) {
        log.info("Request: admin");

        model.addAttribute("users", userQueryService.getAllUsers());
        model.addAttribute("books", bookQueryService.getAllBooks());
        return "admin";
    }
}
