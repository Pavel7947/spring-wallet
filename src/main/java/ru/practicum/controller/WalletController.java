package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.ChangeAmountRequest;
import ru.practicum.dto.WalletDto;
import ru.practicum.service.WalletService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class WalletController {
    private final WalletService walletService;

    @PostMapping("/wallet")
    public WalletDto changeAmount(@RequestBody @Validated ChangeAmountRequest request) {
        log.info("Поступил запрос на изменение баланса счета с телом: {}", request);
        return walletService.changeAmount(request);
    }

    @GetMapping("/wallets/{wallet_UUID}")
    public WalletDto getAmount(@PathVariable(name = "wallet_UUID") UUID walletUUID) {
        log.info("Поступил запрос на получение баланса счета с UUID: {}", walletUUID);
        return walletService.getAmount(walletUUID);
    }
}
