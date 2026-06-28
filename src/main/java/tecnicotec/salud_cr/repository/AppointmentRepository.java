package tecnicotec.salud_cr.repository;

import org.springframework.data.repository.CrudRepository;
import tecnicotec.salud_cr.data.Appointment;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends CrudRepository<Appointment, Long> {

    List<Appointment> findByAppointmentDateAndStatus(
            LocalDateTime appointmentDate,
            Appointment.Status status
    );
    List<Appointment> findByUserId(Long id);
}