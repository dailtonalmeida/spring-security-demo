/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.nom.dsa.springbootsoujava;

import java.util.Arrays;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author dailtonalmeida
 */
@Controller
public class PageController {

    @GetMapping("/home")
    public ModelAndView home() {
        return new ModelAndView("home", "message", "Você está na home page!!!");
    }

    @GetMapping("/todo")
    public String toDo(Model model) {
        model.addAttribute("message", "Você está na todo page!!!");
        model.addAttribute("toDoList", Arrays.asList("Tarefa 1", "Tarefa 2", "Tarefa 3"));
        return "todo";
    }

    @GetMapping("/admin")
    public ModelAndView admin() {
        return new ModelAndView("admin", "message", "Você está na admin page!!!");
    }

}
