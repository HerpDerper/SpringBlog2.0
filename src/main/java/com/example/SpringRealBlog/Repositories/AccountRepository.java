package com.example.SpringRealBlog.Repositories;

import com.example.SpringRealBlog.Models.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {

    Account findAccountByLoginContains(String login);
}