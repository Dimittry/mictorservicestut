package com.microservicestut;


import com.microservicestut.infrastructure.StubService;

import java.util.Random;
import java.util.function.Supplier;

public class App
{
    private static final String[] EXAMPLE_HANDLES = new String[]{
        "aaa", "bbb", "ccc", "ddd", "eee", "fff"
    };
    public static void main( String[] args )
    {
        Random random = new Random();
        new StubService("/users/", 8083, new Supplier<String>() {
            @Override
            public String get() {
                return EXAMPLE_HANDLES[random.nextInt(EXAMPLE_HANDLES.length)];
            }
        }).run();
        System.out.println( "Hello World!" );
    }
}
