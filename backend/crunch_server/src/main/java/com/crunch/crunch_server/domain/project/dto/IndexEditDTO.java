package com.crunch.crunch_server.domain.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IndexEditDTO {
    private int indexId;
    private int projectId;
    private String title;

}
