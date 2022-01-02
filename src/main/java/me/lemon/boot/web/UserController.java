package me.lemon.boot.web;

import me.lemon.boot.bean.User;
import me.lemon.boot.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    UserMapper userMapper;

    @GetMapping("query")
    public String query(Model model) {
        model.addAttribute("users",userMapper.findAll());
        return "user-query";
    }

    @GetMapping("register")
    public String registerPage(){
        return "user-register";
    }

    @PostMapping("register")
    public String register(Model model,
                           @RequestParam("username") String name,
                           @RequestParam("password") String password) {
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        if (userMapper.findByName(user.getName()) != null) {
            model.addAttribute("message","Register Failed!The user is exists!");
            return "message";
        }
        userMapper.insert(user);
        model.addAttribute("message","Register Success!");
        return "message";
    }

    @GetMapping("changePassword")
    public String changePasswordPage() {
        return "user-change-password";
    }

    @PostMapping("changePassword")
    public String changePassword(Model model,
                                 @RequestParam("username") String name,
                                 @RequestParam("oldpasswd") String oldPasswd,
                                 @RequestParam("newpasswd") String newPasswd) {
        User user = new User();
        user.setName(name);
        user.setPassword(newPasswd);
        List<User> users = userMapper.findAll();
        for (User u : users) {
            if (u.getName().equals(name) &&
                u.getPassword().equals(oldPasswd) && !oldPasswd.equals(newPasswd)) {
                model.addAttribute("message","Change Password Success!");
                userMapper.updateByName(user);
                return "message";
            }
        }
        model.addAttribute("message","Change Password Failed!");
        return "message";
    }

    @GetMapping("login")
    public String loginPage(){
        return "user-login";
    }

    @PostMapping("login")
    public String login(Model model,
                        HttpSession session,
                        @RequestParam("username") String name,
                        @RequestParam("password") String password) {
        List<User> users = userMapper.findAll();
        for (User u : users) {
            if (u.getName().equals(name) && u.getPassword().equals(password)) {
                session.setAttribute("username",name);
                return "redirect:/";
            }
        }
        model.addAttribute("message", "Login Failed!");
        return "message";
    }

    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.removeAttribute("username");
        return "redirect:/";
    }

    @GetMapping("delete")
    public String deletePage() {
        return "user-delete";
    }

    @PostMapping("delete")
    public String delete(Model model,
                         HttpSession session,
                         @RequestParam("username") String name,
                         @RequestParam("password") String password) {
        List<User> users = userMapper.findAll();
        for (User u : users) {
            if (u.getName().equals(name) && u.getPassword().equals(password)) {
                userMapper.deleteById(u);
                model.addAttribute("message","Your user is gone.Bye Bye!");
                return "message";
            }
        }
        model.addAttribute("message", "Delete Failed!You lie to me!");
        return "message";
    }
}
