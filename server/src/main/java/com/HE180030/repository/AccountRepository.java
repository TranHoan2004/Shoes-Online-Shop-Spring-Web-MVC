package com.HE180030.repository;

import com.HE180030.model.Account;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    boolean existsAccountByEmail(String email);

    @NotNull Page<Account> findAll(@NotNull Pageable pageable);

    Account findAccountByEmail(String email);
}
