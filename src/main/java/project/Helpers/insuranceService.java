package project.Helpers;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import project.Model.insurance;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для работы с таблицей insurance в базе данных
 * @author lebibop
 */
public class insuranceService {

    /**
     * Создает нового работника в базе данных или обновляет существующего.
     * @param insurance объект, представляющий работника
     * @return true, если транзакция была успешно завершена, иначе false
     * @throws HibernateException если возникла ошибка при работе с Hibernate
     */
    public Boolean createinsurance(insurance insurance) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(insurance);
            transaction.commit();
            return transaction.getStatus() == TransactionStatus.COMMITTED;
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * Обновляет данные о работнике в базе данных.
     * @param insurance объект, представляющий работника
     * @throws HibernateException если возникла ошибка при работе с Hibernate
     */
    public void updateinsurance(insurance insurance) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(insurance);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }

    /**
     * Удаляет данные о работнике из базы данных.
     * @param insurance объект, представляющий работника
     * @throws HibernateException если возникла ошибка при работе с Hibernate
     */
    public void deleteinsurance(insurance insurance) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(insurance);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }

    /**
     * Получает список всех работников из базы данных.
     * @return список объектов, представляющих работников
     * @throws HibernateException если возникла ошибка при работе с Hibernate
     */
    public List<insurance> getinsurances() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from insurance order by conclusion_date desc", insurance.class).list();
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<insurance> getinsurances_expiring() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            LocalDate currentDate = LocalDate.now();
            LocalDate endDate = currentDate.plusMonths(2);
            LocalDate beginDate = currentDate.minusMonths(2);

            String hql = "from insurance where end_date <= :endDateParam and end_date >= :beginDateParam order by end_date desc";
            Query<insurance> query = session.createQuery(hql, insurance.class);
            query.setParameter("endDateParam", endDate);
            query.setParameter("beginDateParam", beginDate);
            return query.list();
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<insurance> getinsurances_signature() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "from insurance where (payments_number = 1 and signature_date1 is null) or (payments_number = 2 and (signature_date1 is null or signature_date2 is null)) order by conclusion_date desc";
            Query<insurance> query = session.createQuery(hql, insurance.class);
            return query.list();
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<insurance> getinsurances_payment() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "from insurance where (payments_number = 1 and payment_date1 is null) or (payments_number = 2 and (payment_date1 is null or payment_date2 is null)) order by conclusion_date desc";
            Query<insurance> query = session.createQuery(hql, insurance.class);
            return query.list();
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }

}