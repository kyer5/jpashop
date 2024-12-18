package jpabook.jpashop.controller;

import jakarta.validation.Valid;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        // 화면에서 MemberForm의 객체에 접근할 수 있게 됨
        model.addAttribute("memberForm", new MemberForm()); // controller에서 view로 넘어갈 때 이 데이터를 넘김
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result) { // @Valid -> MemberForm 내 Validation(@NotEmpty 등) 기능을 쓴다는 것을 인지함
        // Member 엔티티가 아닌, MemberForm으로 데이터 바인딩을 받는 이유: 엔티티 코드가 더러워짐, controller에서 화면으로 넘어올 때의 Validation과 실제 도메인이 원하는 Validation이 다를 수 있음
        // => 화면에 딱 맞는 FormData를 만들어 데이터를 받는 게 좋은 방법이다.

        // BindingResult -> 오류가 담겨서 아래 코드가 실행됨 ->
        if (result.hasErrors()) {
            return "members/createMemberForm"; // 어떤 에러가 있는지 화면에서 뿌릴 수 있음
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        // API를 만들 때에는 이유를 불문하고 절대 Entity를 외부로 반환해선 안 된다 !
        // ↳ ex) Member Entity에 userpassword 필드를 추가했을 때, 1. 패스워드가 그대로 노출되는 문제 2. API 스펙이 변해버리는 문제 (불완전한  API 스펙이 됨)
        List<Member> members = memberService.findMembers(); // 실무에서는 Entity 보단 DTO로 변환해서 뿌리는 것이 좋음
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
