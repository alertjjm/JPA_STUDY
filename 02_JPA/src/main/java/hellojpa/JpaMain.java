package hellojpa;

import javax.persistence.*;
import java.time.LocalDateTime;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf=Persistence.createEntityManagerFactory("hello");
        EntityManager em=emf.createEntityManager();
        em.setFlushMode(FlushModeType.AUTO);
        EntityTransaction tx=em.getTransaction();
        tx.begin();
        try{
            Member member=new Member();
            member.setCreatedBy("JJM");
            member.setCreatedDate(LocalDateTime.now());
            member.setLastModifiedBy("JJM");
            member.setLastModifiedDate(LocalDateTime.now());
            em.persist(member);
            Movie movie=new Movie();
            movie.setDirector("aaa");
            movie.setActor("bbb");
            movie.setName("바람과함께사라지다");
            movie.setPrice(1000);
            em.persist(movie);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
