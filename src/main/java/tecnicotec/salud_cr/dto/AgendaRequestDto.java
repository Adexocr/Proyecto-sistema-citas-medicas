package tecnicotec.salud_cr.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AgendaRequestDto {
    private Integer week = 1;
    private Integer month = 1;
}