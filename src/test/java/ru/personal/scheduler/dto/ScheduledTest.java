package ru.personal.scheduler.dto;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import ru.personal.scheduler.HibernateUtil;

import java.time.Instant;

class ScheduledTest {

    private Scheduled scheduled = Scheduled.Builder(Instant.now(), "example")
            .endDate(Instant.now().plusSeconds(24*60))
            .build();

    @Test
    public void writeNewEntry() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.persist(scheduled.toEntity());
        session.getTransaction().commit();
        session.close();
    }
}