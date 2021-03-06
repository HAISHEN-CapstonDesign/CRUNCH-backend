package com.crunch.crunch_server.domain.crew.controller;

import java.util.List;

import com.amazonaws.services.codebuild.model.Project;
import com.crunch.crunch_server.domain.crew.dto.ApplyingWriterDTO;
import com.crunch.crunch_server.domain.crew.entity.WritersCrew;
import com.crunch.crunch_server.domain.crew.service.BuyerCrewService;
import com.crunch.crunch_server.domain.crew.service.WriterCrewService;
import com.crunch.crunch_server.domain.project.dto.PostFeeDTO;
import com.crunch.crunch_server.domain.project.dto.PostIndexDTO;
import com.crunch.crunch_server.domain.project.dto.ProjectIdDTO;
import com.crunch.crunch_server.domain.project.repository.ProjectRepository;
import com.crunch.crunch_server.domain.project.service.PostService;
import com.crunch.crunch_server.domain.project.service.ProjectService;
import com.crunch.crunch_server.domain.user.service.UserService;
import com.crunch.crunch_server.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api")
public class WriterCrewController {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private WriterCrewService service;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectService projectService;

    @CrossOrigin(origins = "*")
    @PostMapping("/writerapply")
    @ResponseStatus(value = HttpStatus.OK)
    public void WriterApply(@RequestHeader(value = "token") String token,
            @RequestBody ApplyingWriterDTO applyingWriterDTO) {

        //
        int userId = jwtUtil.getUserId(token);
        service.addWriterApply(userId, applyingWriterDTO);

    }

    @CrossOrigin(origins = "*")
    @PostMapping("/mainorapply")
    @ResponseStatus(value = HttpStatus.OK)
    public int MainorApply(@RequestHeader(value = "token") String token, @RequestBody ProjectIdDTO projectIdDTO) {

        //
        int userId = jwtUtil.getUserId(token);
        int mainornot = service.getMainorApply(userId, projectIdDTO.getId());
        System.out.println(mainornot);
        return mainornot;

    }

    @CrossOrigin(origins = "*")
    @PostMapping("/{projectId}/choosewriter")
    @ResponseStatus(value = HttpStatus.OK)
    public List<ApplyingWriterDTO> getApplyingWritersList(@RequestHeader(value = "token") String token,
            @RequestBody ProjectIdDTO projectIdDTO) {
        int userId = jwtUtil.getUserId(token);
        System.out.println(userId);
        System.out.println(projectIdDTO.getId());
        return service.getApplyingWriters(projectIdDTO.getId());
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/{projectId}/choosewritertitle")
    // @ResponseStatus(value = HttpStatus.OK)
    public String getTitle(@PathVariable int projectId) {
        return projectRepository.findById(projectId).getTitle();
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/{projectId}/submitStartFunding")
    @ResponseStatus(value = HttpStatus.OK)
    public int SubmitAndStartFunding(@RequestHeader(value = "token") String token,
            @RequestBody List<Integer> userIdList, @PathVariable int projectId) {
        System.out.println(userIdList);

        service.adoptSelectedWriters(userIdList, projectId);

        return 100;
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/endFunding")
    @ResponseStatus(value = HttpStatus.OK)
    public int endFunding(@RequestHeader(value = "token") String token, @RequestBody ProjectIdDTO projectIdDTO) {

        // userid 와 projectid 를 받아오고
        int userId = jwtUtil.getUserId(token);
        System.out.println("******************");
        System.out.println(projectIdDTO.getId());
        // project state를 writing으로 변경
        projectService.changeProjectStateWriting(projectIdDTO.getId());

        return 100;
    }

}
