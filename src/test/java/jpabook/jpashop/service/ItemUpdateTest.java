package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.item.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemUpdateTest {

    @Autowired
    EntityManager em;

    @Test
    public void updateTest() throws Exception {
        Book book = em.find(Book.class, 1L);
        
        // TX
        book.setName("aaa");

        // 변경감지 == dirty checking
        // TX commit

        // 트렌젝션 안에서 book.setName("aaa");으로 이름을 바꾼 후 트렌잭션이 커밋되면, JPA가 해당 변경문에 대해 찾아 업데이트 쿼리를 자동으로 생성하여 DB에 자동으로 반영함 => 변경 감지
    }
}
