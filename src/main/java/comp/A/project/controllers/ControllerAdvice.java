package comp.A.project.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    private static final Logger log = LoggerFactory.getLogger(ControllerAdvice.class);

    @ExceptionHandler(ResponseStatusException.class)
    public String displayGenericPage(ResponseStatusException e,
                                     Model model) {
        model.addAttribute("status", e.getStatus());
        model.addAttribute("reason", e.getReason());

        return "error";
    }
}
