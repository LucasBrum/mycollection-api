package com.brum.mycollection.api.controller;

import com.brum.mycollection.api.model.Response;
import com.brum.mycollection.api.model.request.CategoryRequest;
import com.brum.mycollection.api.model.response.CategoryResponse;
import com.brum.mycollection.api.service.CategoryService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureJsonTesters
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<CategoryRequest> categoryRequestJson;

    @Autowired
    private JacksonTester<CategoryResponse> categoryResponseJson;

    @MockBean
    private CategoryService categoryService;

    @Test
    @WithMockUser
    @DisplayName("Test try to create a category with a bad request Http Status 400")
    void createWithHttpStatus400Error() throws Exception {
        var response = mockMvc.perform(post("/categories")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @WithMockUser
    @DisplayName("Test try to create a category with a request not found Http Status 404")
    void createWithHttpStatus404Error() throws Exception {
        var response = mockMvc.perform(post("/category")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @WithMockUser
    @DisplayName("Test create a category with success Http Status 201 created")
    void create() throws Exception {
        var response = mockMvc.perform(
                post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertCategoryRequestToJson()))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    private String convertCategoryRequestToJson() throws IOException {
        return categoryRequestJson.write(new CategoryRequest("CD"))
                .getJson();
    }

//    void testList() {
//        var response = mockMvc.perform(
//                get("/categories")
//                    .contentType(MediaType.APPLICATION_JSON)
//                        .get
//        )
//    }
}