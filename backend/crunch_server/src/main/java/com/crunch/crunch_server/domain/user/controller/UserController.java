package com.crunch.crunch_server.domain.user.controller;

import com.crunch.crunch_server.domain.user.dto.UserDTO;
import com.crunch.crunch_server.domain.user.dto.UserMypageDTO;
import com.crunch.crunch_server.domain.user.dto.UserMypageUpdateDTO;

import java.io.IOException;

import com.crunch.crunch_server.domain.user.dto.SessionRequestDTO;

//#region importThings

import com.crunch.crunch_server.domain.user.entity.User;
import com.crunch.crunch_server.domain.user.service.UserMypageService;
import com.crunch.crunch_server.domain.user.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

//#endregion importThings

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private UserMypageService myPageService;

    /// user/account/signup
    @CrossOrigin(origins = "*")
    @PostMapping("/user/account/signup")
    @ResponseStatus(value = HttpStatus.OK)
    public User addUser(@RequestBody UserDTO userDTO) {
        return service.saveUser(userDTO);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/user/mypage")
    public UserMypageDTO returnMapageInfo(@RequestHeader(value = "token") String token) {
        return myPageService.getMyPageInfo(token);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/user/mypage/update")
    @ResponseStatus(value = HttpStatus.OK)
    void updateMyPageInfo(@RequestHeader(value = "token") String token,
            @RequestBody UserMypageUpdateDTO myPageUpdateInfo) throws IOException
    {
        myPageService.updateMyPageInfo(token, myPageUpdateInfo);
    }
    // //have to delete later this is just for developer to check
    // public List<User> addUsers(@RequestBody List<User> users)
    // {
    //     return service.saveUsers(users);
    // }

    // ///users/{userId}/mypage
    // @GetMapping("/user/{userId}/mypage")
    // public User findUserById(@PathVariable int id)
    // {
    //     return service.getUserById(id);
    // }

    // ///users/{userId}/mypage/update
    // @PutMapping("/{userId}/mypage/update")
    // public User updateUserInfo(@RequestBody User user)
    // {
    //     return service.updateUser(user);
    // }

    // ///users/{userId}/mypage
    // @DeleteMapping("/{userId}/mypage")
    // public String deleteUserById(@PathVariable int id)
    // {
    //     return service.deleteUser(id);
    // }

}
