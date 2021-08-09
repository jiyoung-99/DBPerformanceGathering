package com.exem.util.logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;


public class FileLogger implements MyLogger {

    private final Path path;

    public FileLogger(String pathAsString) {

        this.path = Paths.get(pathAsString).toAbsolutePath();

    }


    @Override
    public void error(String message) {
        writeFile(message);
    }

    @Override
    public void warn(String message) {
        writeFile(message);
    }

    @Override
    public void info(String message) {
        writeFile(message);
    }

    @Override
    public void debug(String message) {
        writeFile(message);
    }

    @Override
    public void trace(String message) {
        writeFile(message);
    }


    public void writeFile(String message) {
        try {
            Files.write(this.path, (message + "\n").getBytes(), APPEND, CREATE);
        } catch (IOException e) {
            throw new RuntimeException("Cannot write log message to file [" + this.path + "]", e);
        }
    }

}