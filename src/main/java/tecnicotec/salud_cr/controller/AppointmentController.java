package tecnicotec.salud_cr.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tecnicotec.salud_cr.data.Appointment;
import tecnicotec.salud_cr.dto.AppointmentDto;
import tecnicotec.salud_cr.service.AppointmentService;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public List<Appointment> findAll() {
        return appointmentService.findAll();
    }

    @PostMapping
    public Appointment create(@RequestBody AppointmentDto appointmentDto) {
        return appointmentService.create(appointmentDto);
    }
}