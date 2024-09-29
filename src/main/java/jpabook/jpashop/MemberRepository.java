package jpabook.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {

    @PersistenceContext // Spring Boot가 @PersistenceContext 안에 있으면 EntityManager를 주입해줌
    private EntityManager em;

    // 저장
    public Long save(Member member) {
        em.persist(member);
        return member.getId();
        // ↑ member를 반환하지 않고 id를 반환하는 이유: 'command와 query를 분리해라'라는 원칙에 의해, 저장을 하고 나면 가급적이면 사이드이펙트를 일으키는 커맨드성이기 때문에 return 값을 거의 만들지 않는다. 대신 id 정도 있으면 다음에 다시 조회핳 수 있음 -> 이해 1도 안 됨
    }

    // 조회
    public Member find(Long id) {
        return em.find(Member.class, id);
    }
}
