package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.ChangeAmountRequest;
import ru.practicum.dto.WalletDto;
import ru.practicum.repository.WalletRepository;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;

    @Override
    public WalletDto changeAmount(ChangeAmountRequest request) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public WalletDto getAmount(UUID walletUUID) {
        return null;
    }
}
