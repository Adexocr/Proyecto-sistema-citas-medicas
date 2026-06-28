package tecnicotec.salud_cr.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tecnicotec.salud_cr.data.User;
import tecnicotec.salud_cr.dto.AppointmentDto;
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

    @PostMapping
    public String createAppointment(@AuthenticationPrincipal User user,
                                    @ModelAttribute AppointmentDto newAppointmentDto,
                                    RedirectAttributes redirectAttributes) {
        newAppointmentDto.setUserId(user.getId());
        appointmentService.create(user.getId(), newAppointmentDto);
        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Cita médica agendada exitosamente."
        );
        return "redirect:/user/appointments";
    }

    @PostMapping("/{appointmentId}/cancel")
    public String cancelAppointment(@AuthenticationPrincipal User user,
                                    @PathVariable Long appointmentId,
                                    RedirectAttributes redirectAttributes) {
        appointmentService.cancel(user.getId(), appointmentId);
        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Cita médica cancelada exitosamente."
        );
        return "redirect:/user/appointments";
    }

    @PostMapping("/{appointmentId}/edit")
    public String editAppointment(@AuthenticationPrincipal User user,
                                  @PathVariable Long appointmentId,
                                  @ModelAttribute AppointmentDto updatedAppointmentDto,
                                  RedirectAttributes redirectAttributes) {
        updatedAppointmentDto.setUserId(user.getId());
        appointmentService.updateAppointment(user.getId(), appointmentId, updatedAppointmentDto);
        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Cita médica actualizada exitosamente."
        );
        return "redirect:/user/appointments";
    }
}