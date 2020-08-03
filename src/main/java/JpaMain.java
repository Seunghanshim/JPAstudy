import domain.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Member member = new Member();
            member.setName("ssh");

            Address address = new Address("서울시","이목로 24","xxxx");

            member.setAddress(address);

            em.persist(member);

            tx.commit();
        }catch(Exception e){
            tx.rollback();
        }finally{
            em.close();
        }

        emf.close();
    }
}
