// package com.crunch.crunch_server.domain.project.controller;
package com.crunch.crunch_server.domain.project.controller;

import com.crunch.crunch_server.domain.project.dto.*;

import com.crunch.crunch_server.domain.project.entity.*;
import com.crunch.crunch_server.domain.project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProjectController {

    @Autowired
    private ProjectService service;

    @CrossOrigin(origins = "*")
    @PostMapping("/project/startup")
    @ResponseStatus(value = HttpStatus.OK)
    public int addProject(@RequestHeader(value = "token") String token, @RequestBody ProjectStartDTO projectStartDTO) {
        // System.out.println(projectStartDTO.getTitle());
        // 프로젝트를 save
        System.out.println("heeloo");

        return service.addProject(projectStartDTO);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/collaboProj")
    @ResponseStatus(value = HttpStatus.OK)
    public ProjectStartDTO getRecruitingCollaboProj(@RequestHeader(value = "token") String token,
            @RequestBody ProjectIdDTO projectIdDTO) {
        System.out.println(projectIdDTO.getId());

        // project id로 프로젝트 펀딩금액, 목표 작업기한, 모집 작가 수, 소개 가져오기
        // service.getRecruitingProjectInfo(projectIdDTO.getId());
        System.out.println("heeloo");

        // return service.addProject(projectStartDTO);
        return service.getRecruitingProjectInfo(projectIdDTO.getId());
    }
}
