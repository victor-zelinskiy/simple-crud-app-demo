package ru.javarush.hikarius.testproject.crud.dao;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javarush.hikarius.testproject.crud.model.Page;
import ru.javarush.hikarius.testproject.crud.model.User;

import java.util.List;

@Repository
public class UserDaoHibernateImpl implements UserDao {

    private SessionFactory sessionFactory;

    public UserDaoHibernateImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public UserDaoHibernateImpl() {
    }

    @Override
    @Transactional
    public List<?> getAll() {
        return sessionFactory.getCurrentSession()
                .createCriteria(User.class)
                .list();
    }

    @Override
    @Transactional
    public void saveOrUpdate(User user) {
        sessionFactory.getCurrentSession().saveOrUpdate(user);
    }

    @Override
    @Transactional
    public void delete(int id) {
        sessionFactory.getCurrentSession().delete(new User(id));
        sessionFactory.getCurrentSession().flush();
    }

    @Override
    @Transactional
    public User getById(int id) {
        return (User) sessionFactory.getCurrentSession()
                .createCriteria(User.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
    }

    @Override
    @Transactional
    public Page getPage(int pageNumber, int pageSize, String sortType, String sortField,  String nameFilter, Integer ageMoreFilter, Integer ageLessFilter, Boolean isAdminFilter) {
        Criteria criteria = sessionFactory.getCurrentSession()
                .createCriteria(User.class);
        if (nameFilter != null) {
            criteria.add(Restrictions.like("name", nameFilter, MatchMode.ANYWHERE));
        }
        if (isAdminFilter != null) {
            criteria.add(Restrictions.eq("isAdmin", isAdminFilter));
        }
        if (ageMoreFilter != null) {
            criteria.add(Restrictions.gt("age", ageMoreFilter));
        }
        if (ageLessFilter != null) {
            criteria.add(Restrictions.lt("age", ageLessFilter));
        }
        if ((sortType != null) && (sortField != null)) {
            switch (sortType) {
                case "asc" : criteria.addOrder(Order.asc(sortField));
                    break;
                case "desc" : criteria.addOrder(Order.desc(sortField));
                    break;
            }
        }
        int count = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criteria.setProjection(null);
        List<?> users = criteria.setFirstResult((pageNumber - 1) * pageSize)
                .setMaxResults(pageSize)
                .list();
        return new Page(count, users);
    }
}