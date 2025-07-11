package ru.practicum.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@ToString
@Getter
@Setter
@Entity
@Table(name = "wallets")
public class Wallet {
    @Id
    @UuidGenerator
    private UUID id;
    private Long amount;

}
