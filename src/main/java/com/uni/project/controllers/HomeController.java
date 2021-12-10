package com.uni.project.controllers;

import com.uni.project.entities.User;
import com.uni.project.repository.UserRepository;
import com.uni.project.services.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
public class HomeController extends BaseController {
    private final Integer INITIAL_ROLE_ID = 2;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public ModelAndView index() {
        return this.makeModelAndViewFromViewName("views/home/home.html");
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login() {
        return this.makeModelAndViewFromViewName("views/login.html");
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView loginPost(HttpServletRequest request,
                                  @RequestParam("username") String username,
                                  @RequestParam("password") String password,
                                  RedirectAttributes redirectAttributes) {
       var user =  userRepository.findByUsernameAndPassword(username, password)
               .stream()
               .findFirst().orElse(null);
       if(user == null) {
           redirectAttributes.addFlashAttribute("errors", "Not existing user!");
           return this.makeModelAndViewFromViewName("redirect:login");
       }

       SessionManager.LoginUser(request, username, user.getRole());

       return this.makeModelAndViewFromViewName("redirect:/");
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView register() {
        return this.makeModelAndViewFromViewName("views/register.html");
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView registerPost(@RequestParam("username") String username,
                                     @RequestParam("password") String password,
                                     @RequestParam("password_confirmed") String passwordConfirmed,
                                     RedirectAttributes redirectAttributes) {
        String errors = "";
        if(username.length() < 3 || username.length() > 32 )
        {
            errors += "Username might be between 3 and 32 symbols.\n";
        }

        if(password.length() < 3 || password.length() > 32) {
            errors += "Password might be between 3 and 32 symbols.\n";
        }

        if(!password.equals(passwordConfirmed)) {
            errors += "Password and password confirmed are not equals.\n";
        }

        Optional<User> findUser = userRepository.findByUsernameIs(username).stream().findFirst();

        if(!findUser.isEmpty())
        {
            errors += "Existing username.\n";
        }

        if(errors.length() > 0) {
            redirectAttributes.addFlashAttribute("errors", errors);
            return this.makeModelAndViewFromViewName("redirect:register");
        }

        userRepository.save(new User(username, password, INITIAL_ROLE_ID));
        redirectAttributes.addFlashAttribute("success", "Successfully register! Now you can login!");

        return this.makeModelAndViewFromViewName("redirect:/login");
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request) {
        SessionManager.LogoutUser(request);
        return new ModelAndView("redirect:/");
    }
}
