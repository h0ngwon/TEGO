package prada.teno.global;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
public class HelloController {

    @ApiIgnore
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
