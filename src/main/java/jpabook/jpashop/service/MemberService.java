package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // readOnly = true -> JPA가 조회(읽기)하는 곳에서는 성능을 최적화 함
// @AllArgsConstructor // 모든 필드로 생성자를 만들어줌
@RequiredArgsConstructor // final 필드로만 생성자를 만들어줌
public class MemberService {

    // @Autowired // 스프링이 스프링빈에 등록되어있는 memberrepository를 인젝션해줌
    private final MemberRepository memberRepository; // 인젝션 후 생성자에서 끝날 부분은 final로 선언

    /*// 생성자가 하나만 있는 경우에는 @Autowired가 없어도 자동으로 인젝션해줌
    // @Autowired // 생성자 인젝션, 생성할 때 완성 때문에 중간에 repository가 바뀔 일 x, 테스트 케이스 작성할 때 직접 주입함으로써 memberservice 생성 시 어떤 걸 주입해야 하는지 명시적으로 알 수 있음
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }*/

    /*@Autowired // setter 인젝션, 테스트 코드 작성할 때 Mock을 직접 주입해줄 수 있음 (필드에서는 까다로움),
    public void setMemberRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }*/

    /**
     * 회원가입
     */
    @Transactional // JPA의 모든 데이터 변경은 Transaction 안에서 이루어져야 한다. public 메소드들이 transaction 안에 걸림, 따로 메소드에 선언해주면 우선권을 가짐
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

}
