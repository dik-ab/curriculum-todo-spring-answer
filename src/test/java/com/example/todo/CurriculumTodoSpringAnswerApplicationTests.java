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
        mvc.perform(post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"" + "x".repeat(101) + "\"}"))
            .andExpect(status().isBadRequest());

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

        Thread.sleep(5);

        String newerJson = mvc.perform(post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Review curriculum\"}"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.title", is("Review curriculum")))
            .andReturn()
            .getResponse()
            .getContentAsString();

        long newerId = Long.parseLong(newerJson.replaceAll(".*\"id\":(\\d+).*", "$1"));

        mvc.perform(patch("/todos/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"completed\":true}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.completed", is(true)));

        mvc.perform(get("/todos"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].title", is("Review curriculum")))
            .andExpect(jsonPath("$[1].title", is("Write tests")));

        mvc.perform(delete("/todos/" + newerId))
            .andExpect(status().isNoContent());

        mvc.perform(delete("/todos/" + id))
            .andExpect(status().isNoContent());
    }
}
