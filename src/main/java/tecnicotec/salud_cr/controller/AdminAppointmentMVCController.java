package tecnicotec.salud_cr.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tecnicotec.salud_cr.data.User;
import tecnicotec.salud_cr.dto.AppointmentDto;
import tecnicotec.salud_cr.service.AppointmentService;
import tecnicotec.salud_cr.service.UserService;

@Controller
@RequestMapping("/admin/appointments")
public class AdminAppointmentMVCController {

    private final AppointmentService appointmentService;
    private final UserService userService;

    public AdminAppointmentMVCController(AppointmentService appointmentService,  UserService userService) {
        this.appointmentService = appointmentService;
        this.userService = userService;
    }

    @GetMapping
    public String appointments(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("user",  user);
        model.addAttribute("appointments", appointmentService.findAll());
        model.addAttribute("currentPage", "appointments");
        model.addAttribute("patients", userService.findAllByType(User.Type.REGULAR));
        return "appointments";
    }

    @PostMapping
    public String createAppointment(@ModelAttribute AppointmentDto newAppointmentDto,
                                    RedirectAttributes redirectAttributes) {
        appointmentService.create(newAppointmentDto.getUserId(), newAppointmentDto);
        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Cita médica agendada."
        );
        return "redirect:/admin/appointments";
    }

    @PostMapping("/{appointmentId}/cancel")
    public String cancelAppointment(@AuthenticationPrincipal User user,
                                    @PathVariable Long appointmentId,
                                    RedirectAttributes redirectAttributes) {
        appointmentService.cancelAnyById(appointmentId);
        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Cita médica cancelada."
        );
        return "redirect:/admin/appointments";
    }

    @PostMapping("/{appointmentId}/edit")
    public String editAppointment(@PathVariable Long appointmentId,
                                  @ModelAttribute AppointmentDto updatedAppointmentDto,
                                  RedirectAttributes redirectAttributes) {
        appointmentService.updateAppointment(updatedAppointmentDto.getUserId(), appointmentId, updatedAppointmentDto);
        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Cita médica actualizada."
        );
        return "redirect:/admin/appointments";
    }

    @PostMapping("/{appointmentId}/complete")
    public String completeAppointment(@PathVariable Long appointmentId,
                                      RedirectAttributes redirectAttributes) {
        appointmentService.completeAppointment(appointmentId);
        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Cita médica completada."
        );
        return "redirect:/admin/appointments";
    }
}