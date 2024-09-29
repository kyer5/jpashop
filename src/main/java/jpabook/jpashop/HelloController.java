package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model) { // model에 data를 실어서 Controller에서 View에 넘길 수 있다.
        model.addAttribute("data", "hello!!!"); // data라는 키에 'hello!!!'라는 값을 넘김
        return "hello"; // return <View(화면) 이름(= hello.html)>, '.html'은 자동으로 붙기 때문에 생략 가능, 결과가 화면 렌더링 할 때 hello.html에 나옴
    }
}
