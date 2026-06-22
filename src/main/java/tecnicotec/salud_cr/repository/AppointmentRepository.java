package tecnicotec.salud_cr.repository;

import org.springframework.data.repository.CrudRepository;
import tecnicotec.salud_cr.data.Appointment;

public interface AppointmentRepository extends CrudRepository<Appointment, Long> {
}