package com.mathematics.studentera;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudenteraController {

    @RequestMapping("/hello")
    public String home() {
        return "Hello Student Era!";
    }

    @RequestMapping("/home")
    public String httpHome() {
        String returnStr = StudenteraApplication.additionAndSubtractionWithinTwenty(100, 100, "<br>");
        returnStr = returnStr.replaceAll(" ", "&#160;");

        return "<br><br><center><font size='4'style='line-height:28px;'>" + returnStr + "</font></center>";
    }
}
