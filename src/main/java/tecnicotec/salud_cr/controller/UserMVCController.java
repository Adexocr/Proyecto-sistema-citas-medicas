package tecnicotec.salud_cr.controller;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import tecnicotec.salud_cr.data.User;

@Controller
public class UserMVCController {
    // Pagina de inicio para usuario regular
    @GetMapping("/user/home")
    public String home(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("currentPage", "home");
        return "home";
    }
}
