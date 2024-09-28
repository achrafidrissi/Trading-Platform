package com.tradingplatform.tradeapp.repository;

import com.tradingplatform.tradeapp.model.Wallet;
import com.tradingplatform.tradeapp.model.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {

    Watchlist findByUserId(Long userId);

}
