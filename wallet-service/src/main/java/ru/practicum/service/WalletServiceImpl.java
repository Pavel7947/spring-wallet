package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.ChangeAmountRequest;
import ru.practicum.dto.OperationType;
import ru.practicum.dto.WalletDto;
import ru.practicum.exceptions.LowBalanceException;
import ru.practicum.exceptions.NotFoundException;
import ru.practicum.mapper.WalletDtoMapper;
import ru.practicum.model.Wallet;
import ru.practicum.repository.WalletRepository;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;

    @Transactional
    @Override
    public WalletDto changeAmount(ChangeAmountRequest request) {
        Wallet wallet = walletRepository.findAndLockById(request.getWalletId())
                .orElseThrow(() -> new NotFoundException("По данному UUID кошелек не найден"));
        long currentAmount = wallet.getAmount();
        if (request.getOperationType() == OperationType.WITHDRAW) {
             if (currentAmount < request.getAmount()) {
                throw new LowBalanceException("Недостаточно средств на счете");
             }
            wallet.setAmount(currentAmount - request.getAmount());
        } else {
            wallet.setAmount(currentAmount + request.getAmount());
        }
        return WalletDtoMapper.mapToWalletDto(wallet);
    }

    @Override
    public WalletDto getAmount(UUID walletUUID) {
        return WalletDtoMapper.mapToWalletDto(walletRepository.findById(walletUUID)
                .orElseThrow(() -> new NotFoundException("По данному UUID кошелек не найден")));
    }
}
