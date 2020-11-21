package com.crunch.crunch_server.domain.crew.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class BuyerCrewDTO {
    private int postindexId;
    private int projectId;
    private int userId;
}