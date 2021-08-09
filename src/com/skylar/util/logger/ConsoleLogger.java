package com.exem.util.logger;


public class ConsoleLogger implements MyLogger {


    @Override
    public void error(String message) {
        System.err.println(message);
    }

    @Override
    public void warn(String message) { System.out.println(message);  }

    @Override
    public void info(String message) {
        System.out.println(message);
    }

    @Override
    public void debug(String message) {
        System.out.println(message);
    }

    @Override
    public void trace(String message) {
        System.out.println(message);
    }

}
