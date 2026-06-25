package tecnicotec.salud_cr.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentDto {

    private Long userId;

    private LocalDateTime appointmentDate;

    private String reason;
}