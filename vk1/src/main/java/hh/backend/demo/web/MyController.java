package hh.backend.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class MyController {

    @RequestMapping("/index")
    @ResponseBody
    public String messageMain() {
        return "This is the main page";
    }

    @RequestMapping("/contact")
    @ResponseBody
    public String messageContact() {
        return "This is the contact page";
    }

    @RequestMapping("/hello")
    @ResponseBody
    public String messageHello(@RequestParam (name= "name", required=false, defaultValue="Bob") String name,
                               @RequestParam (name= "location", required=false, defaultValue="Earth") String location) {
        return "Welcome to the " + location + ", " + name + "!";
    }

}
