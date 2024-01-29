package br.com.ibm.persistence.dao;

import java.util.List;

public interface BDao<T> {

    List<T> balanceList(Long userId);

    public void deposit(String accountNumber, Double value);

    public double withdraw(String accountNumber, double value);

    public void transfer(String sourceAccountNumber, String targetAccountNumber, double value);
}