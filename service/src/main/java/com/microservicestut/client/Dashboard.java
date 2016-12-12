package com.microservicestut.client;

import com.microservicestut.client.mood.HappinessChartData;
import com.microservicestut.client.mood.MoodChartData;
import com.microservicestut.client.mood.MoodsParser;
import com.microservicestut.client.mood.TweetMood;
import com.microservicestut.client.user.LeaderboardController;
import com.microservicestut.client.user.LeaderboardData;
import com.microservicestut.infrastructure.ClientEndpoint;
import com.microservicestut.infrastructure.MessageHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Dashboard extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        LeaderboardData leaderboardData = new LeaderboardData();
        MoodChartData moodChartData = new MoodChartData();
        HappinessChartData happinessChartData = new HappinessChartData();

        ClientEndpoint<String> userClient = ClientEndpoint
                .createPassthroughEndpoint("ws://localhost:8083/users/");
        userClient.addListener(leaderboardData);
        userClient.connect();

        ClientEndpoint<TweetMood> moodClientEndpoint
                = new ClientEndpoint<TweetMood>("ws://localhost:8082/moods/", message -> MoodsParser.parse(message));
        moodClientEndpoint.addListener(moodChartData);
        moodClientEndpoint.addListener(happinessChartData);
        moodClientEndpoint.connect();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
        primaryStage.setTitle("Twitter Dashboard");
        Scene scene = new Scene(loader.load(), 1024, 1024);
        scene.getStylesheets().add("dashboard.css");
        System.err.println(leaderboardData);
        DashboardController dashboardController = loader.getController();
        dashboardController.getLeaderboardController().setData(leaderboardData);
        dashboardController.getMoodController().setData(moodChartData);
        dashboardController.getHappinessController().setData(happinessChartData);


        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
