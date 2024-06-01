package itbank.pethub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board")
public class BoardController {

    @GetMapping("/help")
    public void help() {}

    @GetMapping("/list")
    public void list() {}

    @GetMapping("/view")
    public void view() {}

    @GetMapping("/write")
    public void write() {}

    @GetMapping("/wroteBoard")
    public void wroteBoard() {}

    @GetMapping("/wroteReply")
    public void wroteReply() {}
}
