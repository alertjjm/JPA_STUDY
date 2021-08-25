package hellojpa;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf=Persistence.createEntityManagerFactory("hello");
        EntityManager em=emf.createEntityManager();
        em.setFlushMode(FlushModeType.AUTO);
        EntityTransaction tx=em.getTransaction();
        Member member=null;
        Member member2=null;
        System.out.println("==========tx1 start============");
        //tx1
        tx.begin();
        try{
            member= (Member) em.find(Member.class,1L);
            member.setCreatedBy("Jjm");
            System.out.println("==========경계선. 이밑으로 select나가면 안됨============");
            member2= (Member) em.find(Member.class,1L);
            em.persist(member);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }
        System.out.println("==========tx1 end============");
        System.out.println("==========tx2 start============");

        //tx2
        EntityTransaction tx2=em.getTransaction();
        tx2.begin();
        try{
            member= (Member) em.find(Member.class,1L);
            System.out.println(member.getCreatedBy());
        } catch (Exception e) {
            tx2.rollback();
        }
        System.out.println("==========tx2 end============");

        em.close();
        emf.close();
    }
}
