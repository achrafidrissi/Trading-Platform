package com.tradingplatform.tradeapp.repository;

import com.tradingplatform.tradeapp.model.Coin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoinRepository extends JpaRepository<Coin, String> {
}
