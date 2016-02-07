package io.github.janahrens;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class IndexController {

    @RequestMapping(value = "/", method = GET, produces = APPLICATION_JSON_VALUE)
    public String index() {
        return "{\"foo\":\"bar\"}";
    }
}
