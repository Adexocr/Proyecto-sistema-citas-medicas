package tecnicotec.salud_cr.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tecnicotec.salud_cr.data.Appointment;
import tecnicotec.salud_cr.dto.AppointmentDto;
import tecnicotec.salud_cr.service.AppointmentService;

@RestController
@RequestMapping("/api/v1/users/{userId}/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@PathVariable Long userId, @RequestBody AppointmentDto appointmentDto) {
        this.appointmentService.create(userId, appointmentDto);
    }

    @GetMapping
    public Iterable<Appointment> getAll(@PathVariable Long userId) {
        return this.appointmentService.findByUserId(userId);
    }

    @PatchMapping("/{id}")
    public Appointment updateAppointment(@PathVariable Long userId,
                                         @PathVariable Long appointmentId,
                                         @RequestBody AppointmentDto appointment) {
        return this.appointmentService.updateAppointment(userId, appointmentId, appointment);
    }
}
