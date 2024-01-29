package br.com.ibm.persistence.dao;

import br.com.ibm.persistence.model.Account;
import br.com.ibm.persistence.model.AccountType;
import br.com.ibm.persistence.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class BankDao implements BDao<Account> {
    @Inject
    private EntityManager em;
    @Inject
    private UserDao userDao;

    //pega usuário pelo num da conta
    public Optional<User> getByAccountNumber(String accountNumber) {
        TypedQuery<User> query = em.createQuery(
                "SELECT u FROM User u JOIN u.account a WHERE a.accountNumber = :accountNumber", User.class);
        query.setParameter("accountNumber", accountNumber);

        try {
            User user = query.getSingleResult();
            return Optional.of(user);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    //saldo do usuário por lista (id)
    public List<Account> balanceList(Long userId) {
        System.out.println("Debug: Obtendo usuário por ID " + userId);
        User user = userDao.get(userId).orElse(null);

        if (user != null && user.getAccount() != null) {
            return List.of(user.getAccount());
        } else {
            return List.of();
        }
    }

    //deposit do usuário
    @Transactional
    public void deposit(String accountNumber, Double value) {
        //busca usuario apartir do numero da conta
        User user = getByAccountNumber(accountNumber).orElse(null);
        //se tiver um usuario
        if (user != null) {
            Account account = user.getAccount(); //conta associada ao usuário
            if (AccountType.SAVINGS.equals(account.getAccountType())) { //se for poupanca faz o bonus
                value += 0.5;
            }
            account.setBalance(account.getBalance() + value);//att o saldo da conta
            em.merge(account);//persiste as alterações
        }
    }

    //sacar
    @Transactional
    public double withdraw(String accountNumber, double value) {
        User user = getByAccountNumber(accountNumber).orElse(null);

        if (user != null) {
            Account account = user.getAccount();
            double currentBalance = account.getBalance();

            if (currentBalance >= value) {
                account.setBalance(currentBalance - value);
                em.merge(account);

                return account.getBalance();
            }
            return -2; // Indicativo de falha (saldo insuficiente)
        }
        return -1; // Indicativo de falha (conta não encontrada)
    }

    //transferencia
    private static final double TRANSFER_RATE = 0.001;

    @Transactional
    public void transfer(String sourceAccountNumber, String targetAccountNumber, double value) {
        User sourceUser = getByAccountNumber(sourceAccountNumber).orElse(null);
        User targetUser = getByAccountNumber(targetAccountNumber).orElse(null);

        if (sourceUser != null && targetUser != null) {
            Account sourceAccount = sourceUser.getAccount();
            Account targetAccount = targetUser.getAccount();

            if (sourceAccount.getBalance() >= value) {
                double transferValue = value * (1 - TRANSFER_RATE);

                sourceAccount.setBalance(sourceAccount.getBalance() - value);
                targetAccount.setBalance(targetAccount.getBalance() + transferValue);

                em.merge(sourceAccount);
                em.merge(targetAccount);
            }
        }
    }
}
