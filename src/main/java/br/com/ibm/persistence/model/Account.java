package br.com.ibm.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "accountNumber", unique = true, nullable = false)
    private String accountNumber;

    @Column(name = "balance")
    private Double balance;

    @Enumerated(EnumType.STRING)
    @Column(name = "accountType")
    private AccountType accountType;

    @OneToOne
    @JoinColumn(name = "userId", nullable = false)
    @JsonIgnore  // Evita recursividade infinita na serialização JSON
    private User user;
}

