package com.microservicestut.client.twitter;

public interface TweetParser {
    int TEXT_ORDER = 5;
    int NAME_ORDER = 3;
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

    static String getTwitterHandleFrom(String fullTweet) {
        String result;
        String[] tweetParts = fullTweet.split(",");
        String name = tweetParts[NAME_ORDER];
        String[] nameParts = name.split(" ");
        result = nameParts[0].replace("\"", "");
        return (result == null) ? "" : result;
    }
}
