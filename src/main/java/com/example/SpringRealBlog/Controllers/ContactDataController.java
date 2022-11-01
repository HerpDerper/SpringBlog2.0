package com.example.SpringRealBlog.Controllers;

import com.example.SpringRealBlog.Models.ContactData;
import com.example.SpringRealBlog.Models.User;
import com.example.SpringRealBlog.Repositories.ContactDataRepository;
import com.example.SpringRealBlog.Repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class ContactDataController {

    private final UserRepository userRepository;

    private final ContactDataRepository contactDataRepository;

    public ContactDataController(UserRepository userRepository, ContactDataRepository contactDataRepository) {
        this.userRepository = userRepository;
        this.contactDataRepository = contactDataRepository;
    }

    @PostMapping("/contactData/create")
    public String contactDataCreate(@RequestParam long userId, @ModelAttribute("contactData") ContactData contactData, Model model) {
        User user = userRepository.findById(userId).get();
        model.addAttribute("user", user);
        model.addAttribute("adminAccess", true);
        return "ContactData/Create";
    }

    @PostMapping("/contactData/createData")
    public String contactDataCreate(@RequestParam long userId, @ModelAttribute("contactData") @Valid ContactData contactDataValid, BindingResult bindingResult, Model model) {
        User user = userRepository.findById(userId).get();
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            return "ContactData/Create";
        }
        contactDataRepository.save(contactDataValid);
        user.setContactData(contactDataValid);
        userRepository.save(user);
        return "redirect:/user/index";
    }

    @PostMapping("/contactData/edit")
    public String contactDataEdit(@RequestParam long id, Model model) {
        model.addAttribute("adminAccess", true);
        ContactData contactData = contactDataRepository.findById(id).get();
        model.addAttribute("contactData", contactData);
        return "ContactData/Edit";
    }

    @PostMapping("/contactData/editData")
    public String contactDataEdit(@RequestParam long id, @ModelAttribute("contactData") @Valid ContactData contactDataValid, BindingResult bindingResult) {
        ContactData contactData = contactDataRepository.findById(id).get();
        if (bindingResult.hasErrors()) return "ContactData/Edit";
        contactData.setAddress(contactDataValid.getAddress());
        contactData.setEmail(contactDataValid.getEmail());
        contactData.setPhone(contactDataValid.getPhone());
        contactDataRepository.save(contactData);
        return "redirect:/user/index";
    }
}