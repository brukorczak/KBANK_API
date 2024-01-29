package br.com.ibm.persistence.dao;

import br.com.ibm.persistence.model.Account;
import br.com.ibm.persistence.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserDao implements Dao<User> {
    @Inject
    EntityManager em;

    //get do listar saldo
    @Override
    public Optional<User> get(Long id) {
        return Optional.ofNullable(em.find(User.class, id));
    }

    // Busca informações de um usuário por ID (excluindo CPF e senha
    public Optional<User> getUserInfoById(Long id) {
        TypedQuery<User> query = em.createQuery(
                "SELECT NEW br.com.ibm.persistence.model.User(u.id, u.name, u.age, u.phone, u.address) FROM User u WHERE u.id = :id",
                User.class
        );
        query.setParameter("id", id);

        try {
            User user = query.getSingleResult();
            return Optional.of(user);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    //lista todos usuários
    @Override
    public List<User> getAll() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
        criteriaQuery.from(User.class);

        TypedQuery<User> query = em.createQuery(criteriaQuery);
        return query.getResultList();
    }

    //adc usuário
    @Override
    @Transactional
    public Long save(User user) {
        em.persist(user);
        return user.getId();
    }

    //att usuário
    @Override
    @Transactional
    public void update(User user) {
        em.merge(user);
    }

    @Transactional
    public void saveAccount(Account account) {
        em.persist(account);
    }


    //deleta usuário
    @Override
//    @Transactional
//    public Optional<User> delete(Long id) {
//        User user = em.find(User.class, id);
//        if (user != null) {
//            em.remove(user);
//            return Optional.of(user);
//        } else {
//            return Optional.empty();
//        }
//    }
//
    @Transactional
    public Optional<User> delete(Long id) {
        User user = em.find(User.class, id);

        if (user != null) {
            // Verifica se o usuário possui uma conta associada
            if (user.getAccount() != null) {
                Account account = user.getAccount();
                em.remove(account); // Exclui a conta associada
            }

            em.remove(user); // Exclui o usuário
            return Optional.of(user);
        } else {
            return Optional.empty();
        }
    }

    public Optional<User> getUserById(Long id) {
        User user = em.find(User.class, id);
        return Optional.ofNullable(user);
    }

    //verifica se o CPF é único e se o phone é unico
    public boolean isCpfUnique(String cpf) {
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(u) FROM User u WHERE u.cpf = :cpf", Long.class);
        query.setParameter("cpf", cpf);
        return query.getSingleResult() == 0;
    }

    public boolean isPhoneUnique(String phone) {
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(u) FROM User u WHERE u.phone = :phone", Long.class);
        query.setParameter("phone", phone);
        return query.getSingleResult() == 0;
    }

    //verifica se há uma conta com o cpf
    @Transactional
    public Optional<User> getUserByCpf(String cpf) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root).where(builder.equal(root.get("cpf"), cpf));

        TypedQuery<User> query = em.createQuery(criteriaQuery);

        try {
            User user = query.getSingleResult();
            return Optional.of(user);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
