package comp.A.project.controllers;

import comp.A.project.DAO.UserEntity;
import comp.A.project.forms.UserForm;
import comp.A.project.services.User.BookQueryService;
import comp.A.project.services.User.UserCommandService;
import comp.A.project.services.User.UserQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityExistsException;
import javax.validation.Valid;

/*  UserController

    Handles the following routes:
        POST
            /user/create
*/
@Controller
@RequestMapping("/user")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserQueryService userQueryService;
    @Autowired
    private UserCommandService userCommandService;

    @PostMapping("/create")
    public String createUser(@Valid UserForm userForm, BindingResult bindingResult) {
        log.info("Request: create user " + userForm.getUsername());

        if (!bindingResult.hasErrors()) {
            try {
                UserEntity user = userCommandService.createUser(userForm);

                return "redirect:/login";
            } catch (EntityExistsException e) {
                bindingResult.rejectValue("username", "error.user", "That username is already taken");
                log.error(e.getMessage());
            }
        }

        return "register";
    }
}
