package jpabook.jpashop;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional // EntityManager를 통한 모든 데이터 변경은 항상 Transaction 안에서 이루어져야 한다.
    // @Transactional이 Test 케이스에 있으면 Test가 끝난 다음에 바로 DB를 rollback을 한다. -> 데이터가 들어가 있으면 반복적인 테스트를 못하기 때문
    // 일반적으로 Test 아닌 곳에 있으면 정상적으로 동작함
    @Rollback(false) // rollback을 원하지 않을 시
    public void testMember() throws Exception {
        // given
        Member member = new Member();
        member.setUsername("memberA");
        // when
        Long saveId = memberRepository.save(member);
        Member findMember = memberRepository.find(saveId);
        // then
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        Assertions.assertThat(findMember).isEqualTo(member);
        System.out.println("findMember == member: " + (findMember == member)); // 같은 영속성 컨텍스트 안에서는 id 값이 같으면 같은 엔티티로 인식함
    }
}