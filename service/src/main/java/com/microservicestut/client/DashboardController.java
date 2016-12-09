package com.microservicestut.client;

import com.microservicestut.client.mood.HappinessController;
import com.microservicestut.client.mood.MoodController;
import com.microservicestut.client.user.LeaderboardController;
import javafx.fxml.FXML;

public class DashboardController {
    @FXML private LeaderboardController leaderboardController;
    @FXML private MoodController moodController;
    @FXML private HappinessController happyController;

    public LeaderboardController getLeaderboardController() {
        return leaderboardController;
    }

    public MoodController getMoodController() {
        return moodController;
    }

    public HappinessController getHappinessController() {
        return happyController;
    }
}
