package tecnicotec.salud_cr.api;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

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

    // Pagina de inicio para usuario regular
    @GetMapping("/home")
    public String home() {
        return "home";
    }

    // Panel administrativo
    @GetMapping("/admin/usuarios")
    public String adminUsuarios() {
        return "admin/usuarios";
    }
}
