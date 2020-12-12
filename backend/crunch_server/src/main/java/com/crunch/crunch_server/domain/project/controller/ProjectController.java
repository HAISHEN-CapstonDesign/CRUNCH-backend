// package com.crunch.crunch_server.domain.project.controller;
package com.crunch.crunch_server.domain.project.controller;

import java.util.List;

import com.crunch.crunch_server.domain.crew.service.WriterCrewService;
import com.crunch.crunch_server.domain.project.dto.CompletedPostListDTO;
import com.crunch.crunch_server.domain.project.dto.GenreDTO;
import com.crunch.crunch_server.domain.project.dto.MyWritingDTO;
import com.crunch.crunch_server.domain.project.dto.ProjectIdDTO;
import com.crunch.crunch_server.domain.project.dto.ProjectStartDTO;
import com.crunch.crunch_server.domain.project.dto.RecruitingProjectListDTO;
import com.crunch.crunch_server.domain.project.service.*;
import com.crunch.crunch_server.util.JwtUtil;

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

    @Autowired
    private WriterCrewService writerCrewservice;

    @Autowired
    private JwtUtil jwtUtil;

    @CrossOrigin(origins = "*")
    @PostMapping("/project/startup")
    @ResponseStatus(value = HttpStatus.OK)
    public int addProject(@RequestHeader(value = "token") String token, @RequestBody ProjectStartDTO projectStartDTO) {
        // System.out.println(projectStartDTO.getTitle());
        // 프로젝트를 save
        System.out.println("heeloo");
        // writerscrew에 메인작가로 등록
        int userId = jwtUtil.getUserId(token);

        int projectId = service.addProject(projectStartDTO, userId);
        writerCrewservice.addMainWriter(userId, projectId);
        return projectId;
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/collaboProj")
    @ResponseStatus(value = HttpStatus.OK)
    public ProjectStartDTO getRecruitingCollaboProj(@RequestHeader(value = "token") String token,
            @RequestBody ProjectIdDTO projectIdDTO) {

        int userId = jwtUtil.getUserId(token);

        System.out.println(projectIdDTO.getId());

        // project id로 프로젝트 펀딩금액, 목표 작업기한, 모집 작가 수, 소개 가져오기
        // service.getRecruitingProjectInfo(projectIdDTO.getId());
        System.out.println("heeloo");

        // return service.addProject(projectStartDTO);
        return service.getRecruitingProjectInfo(projectIdDTO.getId());
    }

    // List<CompletedPostListDTO>
    @CrossOrigin(origins = "*")
    @PostMapping("/getpostlist")
    @ResponseStatus(value = HttpStatus.OK)
    public List<CompletedPostListDTO> getPostListOfSelectedGenre(@RequestHeader(value = "token") String token,
            @RequestBody GenreDTO genreDTO) {

        // int userId = jwtUtil.getUserId(token);
        System.out.println("====================================");
        System.out.println(genreDTO.getGenre());
        // tagDTO.getTagText()
        return service.getProjectListOfSelectedTag(genreDTO.getGenre());

    }

    @CrossOrigin(origins = "*")
    @PostMapping("/getrecruitingPost")
    @ResponseStatus(value = HttpStatus.OK)
    public List<RecruitingProjectListDTO> getRecruitingPostList(@RequestHeader(value = "token") String token,
            @RequestBody GenreDTO genreDTO) {

        // int userId = jwtUtil.getUserId(token);
        System.out.println("====================================");
        return service.getRecruitingProjectListOfSelectedTag(genreDTO.getGenre());

    }

    @CrossOrigin(origins = "*")
    @PostMapping("/getrecruitingPost")
    @ResponseStatus(value = HttpStatus.OK)
    public List<MyWritingDTO> getMypageWritingList(@RequestHeader(value = "token") String token,
            @RequestBody GenreDTO genreDTO) {

        int userId = jwtUtil.getUserId(token);
        System.out.println("====================================");
        return service.getMyPageWritingProjectList(userId);

    }
}
