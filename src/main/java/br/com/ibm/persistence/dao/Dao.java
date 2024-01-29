package br.com.ibm.persistence.dao;

import br.com.ibm.persistence.model.Account;
import br.com.ibm.persistence.model.User;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {

    Optional<T> get(Long id);

    List<T> getAll();

    Long save(T data);//ver T data

    public void saveAccount(Account account);

    void update(T data);

    Optional<User> delete(Long id);

    Optional<User> getUserById(Long id);
}
