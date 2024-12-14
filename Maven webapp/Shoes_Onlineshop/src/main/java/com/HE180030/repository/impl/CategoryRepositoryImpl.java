package com.HE180030.repository.impl;

import com.HE180030.model.Category;
import com.HE180030.repository.CategoryRepository;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(propagation = Propagation.REQUIRED)
@DependsOn("sessionFactory")
@Repository
public class CategoryRepositoryImpl implements CategoryRepository {
    private final SessionFactory sessionFactory;

    public CategoryRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Category> getAll() {
        return sessionFactory
                .getCurrentSession()
                .createQuery("from Category", Category.class).getResultList();
    }

    @Override
    public Category getByName(String name) {
        return sessionFactory
                .getCurrentSession()
                .createQuery("from Category c where c.name=:name", Category.class)
                .setParameter("name", name)
                .uniqueResult(); // uniqueResult tra ve 1 null, dung getSingleResult thi nem ra 1 exception neu k tim thay
    }

    @Override
    public void insert(int categoryId, String name) {
        Category category = Category.builder()
                .id(categoryId)
                .name(name)
                .build();
        sessionFactory.getCurrentSession().merge(category);
    }
}
