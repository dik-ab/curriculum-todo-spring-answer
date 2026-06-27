package com.example.todo;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CurriculumTodoSpringAnswerApplicationTests {
    @Autowired
    private MockMvc mvc;

    @Test
    void todoCrudFlow() throws Exception {
        String json = mvc.perform(post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Write tests\"}"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.title", is("Write tests")))
            .andExpect(jsonPath("$.completed", is(false)))
            .andReturn()
            .getResponse()
            .getContentAsString();

        long id = Long.parseLong(json.replaceAll(".*\"id\":(\\d+).*", "$1"));

        mvc.perform(patch("/todos/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"completed\":true}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.completed", is(true)));

        mvc.perform(get("/todos"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].title", is("Write tests")));

        mvc.perform(delete("/todos/" + id))
            .andExpect(status().isNoContent());
    }
}
