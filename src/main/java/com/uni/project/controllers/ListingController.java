package com.uni.project.controllers;

import com.uni.project.entities.Comment;
import com.uni.project.entities.Listing;
import com.uni.project.entities.User;
import com.uni.project.repository.CommentRepository;
import com.uni.project.repository.ListingRepository;
import com.uni.project.repository.UserRepository;
import com.uni.project.services.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ListingController extends BaseController {
    @Autowired
    private ListingRepository listingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @RequestMapping(value = "/listings", method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request, Model model){
        model.addAttribute("listings", listingRepository.findAll());
        return this.makeModelAndViewFromViewName("views/listings/index.html");
    }

    @RequestMapping(value = "/listings/{listingId}", method = RequestMethod.GET)
    public ModelAndView preview(HttpServletRequest request, Model model, @PathVariable("listingId") Integer listingId)
    {
        model.addAttribute("comments", commentRepository.findByListingId(listingId));
        model.addAttribute("listing", listingRepository.findById(listingId).orElse(null));
        return this.makeModelAndViewFromViewName("views/listings/preview.html");
    }

    @RequestMapping(value = "/listings/{listingId}", method = RequestMethod.POST)
    public ModelAndView postComment(HttpServletRequest request,
                                    @PathVariable("listingId") Integer listingId,
                                    @RequestParam("text") String text
                                    )
    {

        User u = userRepository.findByUsernameIs(SessionManager.GetUsername(request))
                .stream()
                .findFirst()
                .orElse(null);

        Listing l = listingRepository.findById(listingId).orElse(null);

        commentRepository.save(new Comment(text, u, l));
        return this.makeModelAndViewFromViewName("redirect:/listings/" + listingId);
    }
}
