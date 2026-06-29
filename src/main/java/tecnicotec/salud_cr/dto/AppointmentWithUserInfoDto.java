package tecnicotec.salud_cr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tecnicotec.salud_cr.data.Appointment;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentWithUserInfoDto {
    private Long id;
    private Long userId;
    private String patientName;
    private LocalDateTime appointmentDate;
    private String reason;
    private Appointment.Status status;
    private LocalDateTime createdAt;
}
