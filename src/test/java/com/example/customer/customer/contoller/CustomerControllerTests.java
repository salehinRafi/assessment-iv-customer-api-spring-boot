package com.example.customer.customer.contoller;

import com.example.customer.common.exception.GlobalExceptionHandler;
import com.example.customer.customer.controller.CustomerController;
import com.example.customer.customer.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerTests {

    private final CustomerService service = mock(CustomerService.class);
    private MockMvc mvc;

    @BeforeEach
    void setUp(){
        mvc = MockMvcBuilders.standaloneSetup(new CustomerController(service))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void givenInvalidBodyReq_whenSubmit_thenReturnsBadReq() throws Exception {
        mvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(service);
    }
}
