package ru.practicum.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.dto.WalletDto;
import ru.practicum.model.Wallet;

@UtilityClass
public final class WalletDtoMapper {

    public WalletDto mapToWalletDto(Wallet wallet) {
        return WalletDto.builder()
                .walletId(wallet.getId())
                .amount(wallet.getAmount())
                .build();
    }
}
