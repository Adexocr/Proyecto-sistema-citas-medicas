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

    public List<Appointment> findByUserId(Long userId) {
        return new ArrayList<>(appointmentRepository.findByUserId(userId));
    }

    public void create(Long userId, AppointmentDto appointmentDto) {
        if (userId.equals(appointmentDto.getUserId())) {
            validateAppointmentDate(appointmentDto.getAppointmentDate());
            Appointment appointment = new Appointment();
            appointment.setUserId(appointmentDto.getUserId());
            appointment.setAppointmentDate(appointmentDto.getAppointmentDate());
            appointment.setReason(appointmentDto.getReason());
            appointment.setStatus(Appointment.Status.SCHEDULED);
            appointment.setCreatedAt(LocalDateTime.now());

            appointmentRepository.save(appointment);
        } else {
            throw new RuntimeException("User id invalid");
        }
    }

    public void cancel(Long id) {
        appointmentRepository.findById(id).ifPresent(appointment -> {
            appointment.setStatus(Appointment.Status.CANCELLED);
            appointmentRepository.save(appointment);
        });
    }

    public Appointment updateAppointment(Long userId, Long appointmentId, AppointmentDto appointmentDto) {
        if (userId.equals(appointmentDto.getUserId())) {
            validateAppointmentDate(appointmentDto.getAppointmentDate());

            Appointment appointment = appointmentRepository.findById(appointmentId)
                    .orElseThrow(() -> new RuntimeException("Appointment not found"));

            appointment.setAppointmentDate(appointmentDto.getAppointmentDate());

            if (appointmentDto.getReason() != null && !appointmentDto.getReason().isBlank()) {
                appointment.setReason(appointmentDto.getReason());
            }

            return appointmentRepository.save(appointment);
        } else {
            throw new RuntimeException("User id invalid");
        }
    }

    private void validateAppointmentDate(LocalDateTime appointmentDate) {
        if (appointmentDate == null) {
            throw new RuntimeException("Appointment date missing");
        }

        if (appointmentDate.isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Can't use a past date.");
        }

        List<Appointment> appointments =
                appointmentRepository.findByAppointmentDateAndStatus(
                        appointmentDate,
                        Appointment.Status.SCHEDULED
                );

        if (!appointments.isEmpty()) {
            throw new RuntimeException("Appointment already exists for that time.");
        }
    }
}