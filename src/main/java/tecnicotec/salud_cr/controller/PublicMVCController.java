package tecnicotec.salud_cr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PublicMVCController {

    // Página principal que redirige al login
    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }

    // Pagina de login
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
