package com.tradingplatform.tradeapp.response;

import com.tradingplatform.tradeapp.model.Coin;
import com.tradingplatform.tradeapp.model.User;
import com.tradingplatform.tradeapp.model.Watchlist;

public interface WatchlistService {
    Watchlist findUserWatchlist(Long userId) throws Exception;
    Watchlist createWatchlist(User user);
    Watchlist findById(Long id) throws Exception;
    Coin addItemToWatchlist(Coin coin, User user) throws Exception;

}