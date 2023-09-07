package Hello.hellospring.controller;

import Hello.hellospring.domain.Member;
import Hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping(value = "/members/new")
    public String creat(MemberForm form, RedirectAttributes redirectAttributes) {

        Member member = new Member();
        member.setName(form.getName());

        //궁금하면 찍어볼 수 있음
        System.out.println(form.getName());

        if(!memberService.join2(member)){
            redirectAttributes.addAttribute("status",true);
            return "redirect:/members/new";
        }
        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping(value = "/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
