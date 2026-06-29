package tecnicotec.salud_cr.service;

import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import tecnicotec.salud_cr.data.Appointment;
import tecnicotec.salud_cr.data.User;
import tecnicotec.salud_cr.dto.AgendaRequestDto;
import tecnicotec.salud_cr.dto.AppointmentDto;
import tecnicotec.salud_cr.dto.AppointmentWithUserInfoDto;
import tecnicotec.salud_cr.repository.AppointmentRepository;
import tecnicotec.salud_cr.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;

    public AppointmentService(AppointmentRepository appointmentRepository,  UserRepository userRepository) {
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
    }

    public List<Appointment> findByUserId(Long userId) {
        return new ArrayList<>(appointmentRepository.findByUserId(userId));
    }

    public List<AppointmentWithUserInfoDto> findAll() {
        List<Appointment> appointments = new ArrayList<>();
        appointmentRepository.findAll().forEach(appointments::add);
        return appointments.stream()
                .map(appointment -> {
                    User user = userRepository.findById(appointment.getUserId())
                            .orElseThrow();
                    return new AppointmentWithUserInfoDto(
                            appointment.getId(),
                            appointment.getUserId(),
                            user.getName(),
                            appointment.getAppointmentDate(),
                            appointment.getReason(),
                            appointment.getStatus(),
                            appointment.getCreatedAt()
                    );
                })
                .toList();
    }

    public void create(Long userId, AppointmentDto appointmentDto) {
        if (userId.equals(appointmentDto.getUserId())) {
            if (appointmentDto.getAppointmentDate() != null && appointmentDto.getReason() != null) {

                List<Appointment> sameDateAppointments = getSameDateAppointments(appointmentDto.getAppointmentDate());
                if (!sameDateAppointments.isEmpty()) {
                    throw new  RuntimeException("Another appointment scheduled for the same time");
                }

                Appointment appointment = new Appointment();
                appointment.setUserId(appointmentDto.getUserId());
                appointment.setAppointmentDate(appointmentDto.getAppointmentDate());
                appointment.setReason(appointmentDto.getReason());
                appointment.setStatus(Appointment.Status.SCHEDULED);
                appointment.setCreatedAt(LocalDateTime.now());
                appointmentRepository.save(appointment);
            } else {
                throw new RuntimeException("Invalid appointment input data");
            }
        } else {
            throw new RuntimeException("Invalid user id");
        }
    }

    public void cancel(Long userId, Long id) {
        appointmentRepository.findById(id).ifPresent(appointment -> {
            if (appointment.getAppointmentDate().isBefore(LocalDateTime.now())) {
                throw new RuntimeException("Can't cancel a past appointment.");
            }
            if (appointment.getUserId().equals(userId)) {
                appointment.setStatus(Appointment.Status.CANCELLED);
                appointmentRepository.save(appointment);
            } else {
                throw new RuntimeException("Invalid user id");
            }
        });
    }

    public void cancelAnyById(Long id) {
        appointmentRepository.findById(id).ifPresent(appointment -> {
            if (appointment.getAppointmentDate().isBefore(LocalDateTime.now())) {
                throw new RuntimeException("Can't cancel a past appointment.");
            }
            appointment.setStatus(Appointment.Status.CANCELLED);
            appointmentRepository.save(appointment);
        });
    }

    public void completeAppointment(Long id) {
        appointmentRepository.findById(id).ifPresent(appointment -> {
            if (appointment.getAppointmentDate().isBefore(LocalDateTime.now())) {
                throw new RuntimeException("Can't complete a past appointment.");
            }
            appointment.setStatus(Appointment.Status.COMPLETED);
            appointmentRepository.save(appointment);
        });
    }

    public Appointment updateAppointment(Long userId, Long appointmentId, AppointmentDto appointmentDto) {
        if (userId.equals(appointmentDto.getUserId())) {
            List<Appointment> sameDateAppointments = getSameDateAppointments(appointmentDto.getAppointmentDate())
                    .stream().filter(appointment -> !appointment.getId().equals(appointmentId)).toList();

            if (!sameDateAppointments.isEmpty()) {
                throw new  RuntimeException("Another appointment scheduled for the same time");
            }

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

    private List<Appointment> getSameDateAppointments(LocalDateTime appointmentDate) {
        if (appointmentDate == null) {
            throw new RuntimeException("Appointment date missing");
        }
        if (appointmentDate.isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Can't use a past date.");
        }
        return appointmentRepository.findByAppointmentDateAndStatus(appointmentDate, Appointment.Status.SCHEDULED);
    }

    /**
     * month es 1-12 y week puede ser null
     * Semana 1 = días 1 al 7 del mes
     * Semana 2 = días 8 al 14
     * Semana 3 = días 15 al 21
     * Semana 4 = días 22 al 28
     * Semana 5 = días 29 al final del mes
     * **/
    public List<AppointmentWithUserInfoDto> findScheduledForAgenda(AgendaRequestDto agendaRequestDto) {
        int year = LocalDate.now().getYear();

        LocalDate startDate;
        LocalDate endDate;
        Integer week = agendaRequestDto.getWeek();
        Integer month = agendaRequestDto.getMonth();

        if (week == null) {
            startDate = LocalDate.of(year, month, 1);
            endDate = startDate.plusMonths(1);
        } else {
            LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);

            startDate = firstDayOfMonth.plusWeeks(week - 1L);
            endDate = startDate.plusWeeks(1);

            LocalDate firstDayNextMonth = firstDayOfMonth.plusMonths(1);

            if (endDate.isAfter(firstDayNextMonth)) {
                endDate = firstDayNextMonth;
            }
        }

        List<Appointment> agendaAppointments = appointmentRepository.findAllByStatusAndAppointmentDateGreaterThanEqualAndAppointmentDateLessThanOrderByAppointmentDateAsc(
                Appointment.Status.SCHEDULED,
                startDate.atStartOfDay(),
                endDate.atStartOfDay()
        );

        return agendaAppointments.stream()
                .map(appointment -> {
                    User patient = userRepository.findById(appointment.getUserId())
                            .orElseThrow();
                    return new AppointmentWithUserInfoDto(
                            appointment.getId(),
                            appointment.getUserId(),
                            patient.getName(),
                            appointment.getAppointmentDate(),
                            appointment.getReason(),
                            appointment.getStatus(),
                            appointment.getCreatedAt()
                    );
                })
                .toList();
    }
}