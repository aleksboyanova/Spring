package com.uni.project.controllers;

import com.uni.project.entities.Listing;
import com.uni.project.entities.User;
import com.uni.project.repository.ListingRepository;
import com.uni.project.repository.UserRepository;
import com.uni.project.services.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ManageListingsController extends BaseController {
    @Autowired
    private ListingRepository listingRepository;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/manage/listings", method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request, Model model){
        if(!SessionManager.IsAdmin(request)) {
            return this.makeModelAndViewFromViewName("redirect:/404");
        }

        model.addAttribute("listings", listingRepository.findAll());
        return this.makeModelAndViewFromViewName("views/manage-listings/index.html");
    }

    @RequestMapping(value = "/manage/listings/create", method = RequestMethod.GET)
    public ModelAndView create(HttpServletRequest request, Model model) {
        if(!SessionManager.IsAdmin(request)) {
            return this.makeModelAndViewFromViewName("redirect:/404");
        }

        model.addAttribute("listing", new Listing());
        return this.makeModelAndViewFromViewName("views/manage-listings/form.html");
    }

    @RequestMapping(value = "/manage/listings/create", method = RequestMethod.POST)
    public ModelAndView store(HttpServletRequest request,
                              @RequestParam("title") String title,
                              @RequestParam("description") String description,
                              RedirectAttributes redirectAttributes) {
        if(!SessionManager.IsAdmin(request)) {
            return this.makeModelAndViewFromViewName("redirect:/404");
        }

        User u = userRepository.findByUsernameIs(SessionManager.GetUsername(request))
                               .stream()
                               .findFirst()
                               .orElse(null);

        listingRepository.save(new Listing(title, description, u));
        redirectAttributes.addFlashAttribute("success", "Successfully added listing!");
        return this.makeModelAndViewFromViewName("redirect:/manage/listings");
    }

    @RequestMapping(value = "/manage/listings/{listingId}", method = RequestMethod.GET)
    public ModelAndView edit(HttpServletRequest request,
                             @PathVariable("listingId") Integer listingId, Model model) {
        if(!SessionManager.IsAdmin(request)) {
            return this.makeModelAndViewFromViewName("redirect:/404");
        }

        Listing l = listingRepository.findById(listingId).orElse(null);
        if(l == null) {
            return this.makeModelAndViewFromViewName("redirect:/404");
        }

        model.addAttribute("listing", l);
        return this.makeModelAndViewFromViewName("views/manage-listings/form.html");
    }

    @RequestMapping(value = "/manage/listings/{listingId}", method = RequestMethod.POST)
    public ModelAndView update(HttpServletRequest request,
                               @PathVariable("listingId") Integer listingId,
                               Model model,
                               @RequestParam("title") String title,
                               @RequestParam("description") String description,
                               RedirectAttributes redirectAttributes) {
        if(!SessionManager.IsAdmin(request)) {
            return this.makeModelAndViewFromViewName("redirect:/404");
        }

        Listing l = listingRepository.findById(listingId).orElse(null);
        if(l == null) {
            return this.makeModelAndViewFromViewName("redirect:/404");
        }

        l.setTitle(title);
        l.setDescription(description);
        listingRepository.save(l);
        redirectAttributes.addFlashAttribute("success", "Successfully edited listing!");

        return this.makeModelAndViewFromViewName("redirect:/manage/listings");
    }

    @RequestMapping(value = "/manage/listings/delete/{listingId}", method = RequestMethod.POST)
    public ModelAndView delete(HttpServletRequest request,
                               @PathVariable("listingId") Integer listingId,
                               RedirectAttributes redirectAttributes)
    {
        if(!SessionManager.IsAdmin(request)) {
            return this.makeModelAndViewFromViewName("redirect:/404");
        }
        listingRepository.deleteById(listingId);
        redirectAttributes.addFlashAttribute("success", "Successfully edited listing!");
        return this.makeModelAndViewFromViewName("redirect:/manage/listings");
    }
}
