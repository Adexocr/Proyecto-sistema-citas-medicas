package tecnicotec.salud_cr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import tecnicotec.salud_cr.service.AppointmentService;

@Controller
@RequestMapping("/appointments")
public class AppointmentMVCController {

    private final AppointmentService appointmentService;

    public AppointmentMVCController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }
}