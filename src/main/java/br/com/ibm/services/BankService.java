package br.com.ibm.services;

import br.com.ibm.exception.BadRequestException;
import br.com.ibm.exception.NotFoundException;
import br.com.ibm.exception.SaldoInsuficienteException;
import br.com.ibm.persistence.dao.BankDao;
import br.com.ibm.persistence.dao.UserDao;
import br.com.ibm.persistence.dto.AccountDto;
import br.com.ibm.persistence.model.Account;
import br.com.ibm.persistence.model.AccountType;
import br.com.ibm.persistence.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class BankService {
    @Inject
    BankDao bankDao;
    @Inject
    UserDao userDao;

    public void createAccount(Long userId, String accountType) {
        Optional<User> userOptional = this.userDao.getUserById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            Account account = new Account();
            account.setAccountNumber(generateAccountNumber(userId));
            account.setBalance(0.0);
            account.setAccountType(AccountType.valueOf(accountType));

            account.setUser(user);
            // Salve a conta dentro da transação
            this.userDao.saveAccount(account);
            // Atualize o usuário para refletir a associação
            user.setAccount(account);
            this.userDao.update(user);
        } else {
            throw new NotFoundException("Usuário não encontrado para associar conta");
        }
    }

    private String generateAccountNumber(Long userId) {
        return String.format("%06d", userId);
    }

    public List<AccountDto> balanceList(Long userId) {
        Optional<User> userOptional = this.userDao.getUserById(userId);

        if (userOptional.isPresent()) {
            List<Account> accounts = this.bankDao.balanceList(userId);
            return accounts.stream()
                    .map(account -> {
                        AccountDto accountDto = new AccountDto();
                        accountDto.setAccountNumber(account.getAccountNumber());
                        accountDto.setBalance(account.getBalance());
                        accountDto.setAccountType(account.getAccountType().name());
                        accountDto.setUserName(account.getUser().getName());
                        return accountDto;
                    })
                    .collect(Collectors.toList());
        } else {
            throw new BadRequestException("Usuário não encontrado.");
        }
    }

    public void deposit(String accountNumber, double value) {
        User user = bankDao.getByAccountNumber(accountNumber).orElse(null);
        if (user == null) {
            throw new NotFoundException("Conta não encontrada.");
        }
        this.bankDao.deposit(accountNumber, value);//realiza a operacao de deposit
    }

    //melhore com optional
    public double withdraw(String accountNumber, double value) {
        User user = bankDao.getByAccountNumber(accountNumber).orElse(null);
        if (user == null) {
            throw new NotFoundException("Conta não encontrada.");
        }
        double newBalance = this.bankDao.withdraw(accountNumber, value);
        if (newBalance == -2) {
            throw new SaldoInsuficienteException("Saldo insuficiente na conta de " + accountNumber + ".");
        }
        return newBalance;
    }

    public void transfer(String sourceAccountNumber, String targetAccountNumber, double value) {
        User sourceUser = bankDao.getByAccountNumber(sourceAccountNumber).orElse(null);
        User targetUser = bankDao.getByAccountNumber(targetAccountNumber).orElse(null);

        if (sourceUser == null) {
            throw new NotFoundException("Conta origem não encontrada.");
        } else if (targetUser == null) {
            throw new NotFoundException("Conta destino não encontrada.");
        }

        try {
            Account sourceAccount = sourceUser.getAccount();
            if (sourceAccount.getBalance() < value) {
                throw new SaldoInsuficienteException("Saldo insuficiente para a transferência.");
            }

            this.bankDao.transfer(sourceAccountNumber, targetAccountNumber, value);
        } catch (SaldoInsuficienteException e) {
            throw new SaldoInsuficienteException("Saldo insuficiente para a transferência.");
        }
    }
}
