package project;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

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
            return session.createQuery("from insurance", insurance.class).list();
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }

}