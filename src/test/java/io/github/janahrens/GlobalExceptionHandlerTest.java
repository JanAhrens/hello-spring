package io.github.janahrens;

import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.DispatcherServlet;

import java.lang.reflect.Field;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GlobalExceptionHandlerTest {
    MockMvc mockMvc;

    @Controller
    private class DummyController {}

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(new DummyController())
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    public void testErrorHandling() throws Exception {
        throwExceptionIfNoHandlerFound();

        RequestBuilder request = get("/notFound")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error", Is.is("NOT_FOUND")));
    }

    // Copied from:
    // https://github.com/zalando/problem-spring-web/blob/9f77f385b424c825d8d074e7bb311ec315780afe/src/test/java/org/zalando/problem/spring/web/advice/routing/NoHandlerFoundAdviceTraitTest.java#L69
    //
    // WARNING:
    // It's also necessary to set "spring.mvc.throw-exception-if-no-handler-found" to "true" or else the test is useless.
    private void throwExceptionIfNoHandlerFound() throws NoSuchFieldException, IllegalAccessException {
        final Field field = MockMvc.class.getDeclaredField("servlet");
        field.setAccessible(true);
        final DispatcherServlet servlet = (DispatcherServlet) field.get(mockMvc);
        servlet.setThrowExceptionIfNoHandlerFound(true);
    }

}
