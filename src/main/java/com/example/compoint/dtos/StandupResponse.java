package com.example.compoint.dtos;

import com.example.compoint.dtos.userDTO.UserSummaryDTO;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StandupResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private UserSummaryDTO user;
    private StandupInfoDTO standupInfo;
}

