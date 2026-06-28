package tecnicotec.salud_cr.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tecnicotec.salud_cr.data.User;
import tecnicotec.salud_cr.service.AppointmentService;

@Controller
@RequestMapping("/user/appointments")
public class AppointmentMVCController {

    private final AppointmentService appointmentService;

    public AppointmentMVCController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public String userAppointments(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("user",  user);
        model.addAttribute("appointments", appointmentService.findByUserId(user.getId()));
        model.addAttribute("currentPage", "appointments");
        return "appointments";
    }
}