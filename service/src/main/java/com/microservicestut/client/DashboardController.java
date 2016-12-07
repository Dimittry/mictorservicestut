package com.microservicestut.client;

import com.microservicestut.client.user.LeaderboardController;
import javafx.fxml.FXML;

public class DashboardController {
    @FXML private LeaderboardController leaderboardController;

    public LeaderboardController getLeaderboardController() {
        return leaderboardController;
    }
}
