package ru.practicum.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ChangeAmountRequest {
    @NotNull(message = "UUID кошелька не указан")
    private UUID walletId;
    @NotNull(message = "Тип операции не указан")
    private OperationType operationType;
    @Positive(message = "Сумма должна быть положительной")
    private Long amount;
}
