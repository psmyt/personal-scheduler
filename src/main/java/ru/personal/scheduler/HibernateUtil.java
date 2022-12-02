package ru.personal.scheduler;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.personal.scheduler.entities.ScheduledEntity;

public class HibernateUtil {

    private static final SessionFactory sessionFactory;
    private static final Configuration configuration = new Configuration()
            .addAnnotatedClass(ScheduledEntity.class)
            .configure();

    static {
        try {
            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable e) {
            System.err.println("Initial SessionFactory creation failed." + e);
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
