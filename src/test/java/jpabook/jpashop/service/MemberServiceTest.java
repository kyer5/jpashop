package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class) // JUnit 실행할 때 Spring이랑 같이 실행
@SpringBootTest // SpringBoot를 띄운 상태로 실행하기 위해 필요
@Transactional // 테스트 케이스 내에서 사용 시 테스트가 끝나면 rollback 함 (service, repository에서는 rollback 하지 않음)
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Test
//    @Rollback(false) // rollback을 안 하고 commit 함 -> insert문 나감 (진짜 DB에 들어가는지 확인하고 싶을 때 사용)
    public void 회원가입() throws Exception {
        // given
        Member member = new Member();
        member.setName("kim");

        // when
        Long savedId = memberService.join(member);

        // then
//        em.flush(); // rollback이지만 DB에 query 날리는 걸 보고 싶을 때 사용, join(member)에 있는 member 객체가 쿼리에 DB로 반영된다.
        assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test(expected = IllegalStateException.class) // 아래 try catch 예외 구문과 동일한 기능
    public void 중복_회원_예외() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        // when
        memberService.join(member1);
        /*try {
            memberService.join(member2);
        } catch (IllegalStateException e) {
            return;
        }*/
        memberService.join(member2); // 예외가 발생해야 한다. (똑같은 이름을 넣었기 때문, memberService.validateDuplicateMember())

        // then
        fail("예외가 발생해야 한다.");
    }

}