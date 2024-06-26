package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.models.Person;
import ru.kata.spring.boot_security.demo.service.PeopleService;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.util.PersonValidator;

import javax.validation.Valid;

@Controller
public class AdminController {
    private final PeopleService peopleService;
    private final RoleService roleService;


    public AdminController(PeopleService peopleService, RoleService roleService) {
        this.peopleService = peopleService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String getAllUsers(Model model) {
        model.addAttribute("people", peopleService.getUsersList());
        return "users/admin";
    }

    @GetMapping("/admin/edit")
    public String editUser(@RequestParam("id") int id, Model model) {
        model.addAttribute("person", peopleService.getUserByID(id));
        model.addAttribute("roles", roleService.getAllRoles());
        return "users/editUser";
    }

    @PostMapping("/admin/update")
    public String updateUser(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                             @RequestParam("id") int id) {

        if (bindingResult.hasErrors()) {
            return "users/editUser";
        }

        peopleService.updateUser(id, person);
        return "redirect:/admin";
    }

    @PostMapping("/admin/delete")
    public String deleteUser(@RequestParam("id") int id) {
        peopleService.deleteUser(id);
        return "redirect:/admin";
    }
}
