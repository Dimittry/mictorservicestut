package com.microservicestut.client.user;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LeaderboardData {
    private ObservableList<TwitterUser> items = FXCollections.observableArrayList();

    public ObservableList<TwitterUser> getItems() {
        return items;
    }

}
