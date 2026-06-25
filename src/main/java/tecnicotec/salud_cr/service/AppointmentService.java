package tecnicotec.salud_cr.service;

import org.springframework.stereotype.Service;
import tecnicotec.salud_cr.data.Appointment;
import tecnicotec.salud_cr.dto.AppointmentDto;
import tecnicotec.salud_cr.repository.AppointmentRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public List<Appointment> findAll() {
        List<Appointment> appointments = new ArrayList<>();
        appointmentRepository.findAll().forEach(appointments::add);
        return appointments;
    }

    public Appointment create(AppointmentDto appointmentDto) {
        Appointment appointment = new Appointment();

        appointment.setUserId(appointmentDto.getUserId());
        appointment.setAppointmentDate(appointmentDto.getAppointmentDate());
        appointment.setReason(appointmentDto.getReason());
        appointment.setStatus(Appointment.Status.SCHEDULED);
        appointment.setCreatedAt(LocalDateTime.now());

        return appointmentRepository.save(appointment);
    }

    public void cancel(Long id) {
        appointmentRepository.findById(id).ifPresent(appointment -> {
            appointment.setStatus(Appointment.Status.CANCELLED);
            appointmentRepository.save(appointment);
        });
    }
}