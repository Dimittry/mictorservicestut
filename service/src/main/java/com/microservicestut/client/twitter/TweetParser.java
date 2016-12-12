package com.microservicestut.client.twitter;

public interface TweetParser {
    static int TEXT_ORDER = 5;
    static String getTweetMessageFrom(String fullTweet) {
        System.err.println("FULLTWEET");
        System.err.println(fullTweet);
//        String textFieldName = "\"text\"";
//        String nextFieldName = "\"retweeted_status_id\"";
//        int indexOfTextField = fullTweet.indexOf(textFieldName) + textFieldName.length();
//        int indexOfEndOfText = fullTweet.indexOf(nextFieldName);

//        return fullTweet.substring(indexOfTextField, indexOfEndOfText);
        return fullTweet.split(",")[TEXT_ORDER];
    }

//    static String getTwitterHandleFrom(String fullTweet) {
//        String twitterHandleFieldName = ""
//    }
}
