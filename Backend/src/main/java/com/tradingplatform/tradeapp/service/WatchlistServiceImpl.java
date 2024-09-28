package com.tradingplatform.tradeapp.service;

import com.tradingplatform.tradeapp.model.Coin;
import com.tradingplatform.tradeapp.model.User;
import com.tradingplatform.tradeapp.model.Watchlist;
import com.tradingplatform.tradeapp.repository.WatchlistRepository;
import com.tradingplatform.tradeapp.response.WatchlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WatchlistServiceImpl implements WatchlistService {

    @Autowired
    private WatchlistRepository watchlistRepository;

    @Override
    public Watchlist findUserWatchlist(Long userId) throws Exception {
        Watchlist watchlist = watchlistRepository.findByUserId(userId);
        if (watchlist == null) {
            throw new Exception("watchlist not found");
        }
        return watchlist;

    }

    @Override
    public Watchlist createWatchlist(User user) {
        Watchlist watchlist = new Watchlist();
        watchlist.setUser(user);
        return watchlistRepository.save(watchlist);
    }

    @Override
    public Watchlist findById(Long id) throws Exception {
        Optional<Watchlist> watchlist = watchlistRepository.findById(id);
        if (watchlist.isEmpty()) {
            throw new Exception("watchlist not found");
        }
        return watchlist.get();
    }

    @Override
    public Coin addItemToWatchlist(Coin coin, User user) throws Exception {
        Watchlist watchlist = findUserWatchlist(user.getId());

        if (watchlist.getCoins().contains(coin)) {
            watchlist.getCoins().remove(coin);
        } else {
            watchlist.getCoins().add(coin);
        }
        watchlistRepository.save(watchlist);
        return coin;

    }
}
