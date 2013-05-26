package com.movie.web.controllers;

import com.movie.errors.ResourceNotFoundException;
import com.movie.pers.entities.Movie;
import com.movie.pers.entities.User;
import com.movie.web.context.UserContext;
import com.movie.web.service.MovieService;
import com.movie.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 * @author Aloren
 */
@Controller
public class FavoritesController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserContext userContext;
    @Autowired
    private MovieService movieService;

    @RequestMapping(value = "/addToFavorites/{movieId}", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public String addMovieToFavorites(@PathVariable("movieId") int movieId,
            HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        System.out.println("Adding to favorites!");
        User user = userContext.getCurrentUser();
        if (user != null) {
            Movie movie = movieService.findById(movieId);
            if (movie != null) {
                userService.addUserFavorite(user.getId(), movie);
                return "redirect:"+referer;
            } else {
                throw new ResourceNotFoundException("No such film!");
            }
        }
        return "errors/403";
    }
    
    @RequestMapping(value = "/removeFavorites/{movieId}", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public String removeMovieFromFavorites(@PathVariable("movieId") int movieId,
            HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        System.out.println("Removing favorites!");
        User user = userContext.getCurrentUser();
        if (user != null) {
            Movie movie = movieService.findById(movieId);
            if (movie != null) {
                userService.removeUserFavorite(user.getId(), movie);
                return "redirect:"+referer;
            } else {
                throw new ResourceNotFoundException("No such film!");
            }
        }
        return "errors/403";
    }

    @RequestMapping(value = "/hasFavorite/{movieId}", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public boolean hasFavorite(@PathVariable("movieId") int movieId) {
        User user = userContext.getCurrentUser();
        if (user != null) {
            System.out.println("Has favorite user " + user.getId() + " movieId=" + movieId);
            //TODO temp solution
            List<Movie> movies = userService.getUserFavoriteMovies(user.getId());
            for (Movie movie : movies) {
                if (movie.getId() == movieId) {
                    System.out.println("Returning true");
                    return true;
                }
            }
        }
        System.out.println("Returning false;");
        return false;
    }
}
