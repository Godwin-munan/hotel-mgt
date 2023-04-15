package com.munan.hotelmgt.dto;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class JobDto {
    private String title;
    private Double salary;
    private String description;
}
