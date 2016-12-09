package com.microservicestut;


import com.microservicestut.infrastructure.StubService;

import java.util.Random;

public class MoodStubServiceTest
{
    private static final String[] POSSIBLE_MOODS = new String[]{
        "HAPPY", "SAD", "HAPPY,SAD"
    };
    public static void main( String[] args )
    {
        Random random = new Random();
        new StubService("/moods/", 8084, () -> POSSIBLE_MOODS[random.nextInt(POSSIBLE_MOODS.length)]).run();
        System.out.println( "Hello World!" );
    }
}
