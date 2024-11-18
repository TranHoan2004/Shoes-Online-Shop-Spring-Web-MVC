package com.HE180030.repository.impl;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@DependsOn("sessionFactory")
@Repository
public class CartRepositoryImpl {
}
