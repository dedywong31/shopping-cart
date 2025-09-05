package org.playground.shoppingcart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @RequestMapping("/user")
    public String index(Model model) {
        model.addAttribute("name", "Dedy");
        return "index";
    }
}
