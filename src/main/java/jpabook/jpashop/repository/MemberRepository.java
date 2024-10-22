package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em; // spring이 entitymanager를 만들어서 주입해줌

    public void save(Member member) {
        em.persist(member); // jpa가 저장함
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id); // 멤버를 찾아서 반환, 단건 조회 (타입, PK)
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class) // jpql -> sql과 비슷, but from의 대상이 테이블이 아니라 Entity임
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class) // :member -> 파라미터 바인딩
                .setParameter("name", name)
                .getResultList();
    }
}
