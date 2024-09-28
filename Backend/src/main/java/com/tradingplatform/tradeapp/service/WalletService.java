package com.tradingplatform.tradeapp.service;

import com.tradingplatform.tradeapp.model.Order;
import com.tradingplatform.tradeapp.model.User;
import com.tradingplatform.tradeapp.model.Wallet;

public interface WalletService {
    Wallet getUserWallet(User user);

    Wallet addBalance(Wallet wallet, Long money);

    Wallet findWalletById(Long id) throws Exception;

    Wallet walletToWalletTransfer(User sender, Wallet receiverWallet, Long amount) throws Exception;

    Wallet payOrderPayment(Order order, User user) throws Exception;

}
