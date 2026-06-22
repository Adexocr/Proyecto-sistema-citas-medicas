package tecnicotec.salud_cr.data;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table("appointments")
public class Appointment {

    @Id
    private Long id;

    @Column("user_id")
    private Long userId;

    @Column("appointment_date")
    private LocalDateTime appointmentDate;

    private String reason;

    private Status status;

    @Column("created_at")
    private LocalDateTime createdAt;

    public enum Status {
        SCHEDULED,
        CANCELLED,
        COMPLETED
    }
}