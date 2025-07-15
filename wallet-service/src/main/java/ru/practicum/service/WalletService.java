package ru.practicum.service;

import ru.practicum.dto.ChangeAmountRequest;
import ru.practicum.dto.WalletDto;

import java.util.UUID;

public interface WalletService {

    WalletDto changeAmount(ChangeAmountRequest request);

    WalletDto getAmount(UUID walletUUID);
}
