package com.crunch.crunch_server.domain.community.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.webservices.client.AutoConfigureWebServiceClient;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.web.JsonPath;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.crunch.crunch_server.domain.community.dto.FirstCommunityBlobDTO;
import com.crunch.crunch_server.domain.community.entity.Community;
import com.crunch.crunch_server.domain.community.repository.ChatRepository;
import com.crunch.crunch_server.domain.community.repository.ChatRoomRepository;

@RunWith(SpringRunner.class) 
@WebMvcTest(controllers = CommunityContoller.class)
@Import(CommunityContoller.class)
@AutoConfigureMockMvc
public class CommunityContollerTest {
    
    @Autowired
    private MockMvc mvc;

    @MockBean
    CommunityContoller communityController;


    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup(){
        mvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .alwaysDo(print())
                .build();
    
    }


    @Test
    public void checkGeneralCommunityBlobDTO() throws Exception {
 
        //just for annotation
        int projectTestId = 172;
        int indexTestId = 0;

        mvc.perform(MockMvcRequestBuilders.get("/api/project/172/index/0/CommunityBlob")
        )
                .andExpect(status().isOk());
    }

}
