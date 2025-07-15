package ru.practicum.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.dto.ChangeAmountRequest;
import ru.practicum.dto.OperationType;
import ru.practicum.dto.WalletDto;
import ru.practicum.exceptions.LowBalanceException;
import ru.practicum.exceptions.NotFoundException;
import ru.practicum.service.WalletService;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WalletController.class)
class WalletControllerTest {
    @MockBean
    WalletService walletService;
    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper mapper;
    static WalletDto walletDto;
    ChangeAmountRequest changeAmountRequest;
    static final UUID WALLET_UUID = UUID.fromString("6d883f20-1fbf-4354-94d3-760a5d77def8");


    @BeforeAll
    static void initiateStaticFields() {
        walletDto = WalletDto.builder()
                .walletId(WALLET_UUID)
                .amount(1000L)
                .build();
    }

    @BeforeEach
    void setUp() {
        changeAmountRequest = ChangeAmountRequest.builder()
                .walletId(WALLET_UUID)
                .amount(1000L)
                .operationType(OperationType.DEPOSIT)
                .build();
    }


    @Test
    void changeAmount_whenInvoked_thenReturnWalletDto() throws Exception {
        when(walletService.changeAmount(changeAmountRequest)).thenReturn(walletDto);
        mvc.perform(post("/api/v1/wallet")
                        .content(mapper.writeValueAsString(changeAmountRequest))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(walletDto)));
    }

    @Test
    void getAmount_whenInvoked_thenReturnWalletDto() throws Exception {
        when(walletService.getAmount(WALLET_UUID)).thenReturn(walletDto);
        mvc.perform(get("/api/v1/wallets/" + WALLET_UUID)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(walletDto)));
    }

    @Test
    void getAmount_whenWalletNotFound_thenReturn404() throws Exception {
        when(walletService.getAmount(WALLET_UUID)).thenThrow(NotFoundException.class);
        mvc.perform(get("/api/v1/wallets/" + WALLET_UUID)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void changeAmount_whenBalanceIsLow_thenReturn400() throws Exception {
        when(walletService.changeAmount(changeAmountRequest)).thenThrow(LowBalanceException.class);
        mvc.perform(post("/api/v1/wallet")
                        .content(mapper.writeValueAsString(changeAmountRequest))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void changeAmount_whenWalletNotFount_thenReturn404() throws Exception {
        when(walletService.changeAmount(changeAmountRequest)).thenThrow(NotFoundException.class);
        mvc.perform(post("/api/v1/wallet")
                        .content(mapper.writeValueAsString(changeAmountRequest))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void changeAmount_whenOperationTypeIsAbsent_thenReturn400() throws Exception {
        changeAmountRequest.setOperationType(null);
        mvc.perform(post("/api/v1/wallet")
                        .content(mapper.writeValueAsString(changeAmountRequest))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void changeAmount_whenAmountIsNegative_thenReturn400() throws Exception {
        changeAmountRequest.setAmount(-1L);
        mvc.perform(post("/api/v1/wallet")
                        .content(mapper.writeValueAsString(changeAmountRequest))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void changeAmount_whenAmountIsNull_thenReturn400() throws Exception {
        changeAmountRequest.setAmount(0L);
        mvc.perform(post("/api/v1/wallet")
                        .content(mapper.writeValueAsString(changeAmountRequest))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void changeAmount_whenIdIsAbsent_thenReturn400() throws Exception {
        changeAmountRequest.setWalletId(null);
        mvc.perform(post("/api/v1/wallet")
                        .content(mapper.writeValueAsString(changeAmountRequest))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void changeAmount_whenJsonFormatIsNotValid_thenReturn400() throws Exception {
        changeAmountRequest.setWalletId(null);
        mvc.perform(post("/api/v1/wallet")
                        .content(mapper.writeValueAsString(changeAmountRequest).replace('{', '}'))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}