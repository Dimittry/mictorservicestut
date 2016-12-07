package com.microservicestut.client;

import com.microservicestut.client.user.LeaderboardController;
import com.microservicestut.client.user.LeaderboardData;
import com.microservicestut.infrastructure.ClientEndpoint;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Dashboard extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        LeaderboardData leaderboardData = new LeaderboardData();

        ClientEndpoint<String> userClient = ClientEndpoint
                .createPassthroughEndpoint("ws://localhost:8083/users/");
        userClient.addListener(leaderboardData);
        userClient.connect();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
        primaryStage.setTitle("Twitter Dashboard");
        Scene scene = new Scene(loader.load(), 1024, 1024);
        scene.getStylesheets().add("dashboard.css");

        DashboardController dashboardController = loader.getController();
        dashboardController.getLeaderboardController().setData(leaderboardData);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
