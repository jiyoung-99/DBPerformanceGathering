package com.skylar.util.logger;


public class LoggerFactory {

    public static Integer level;

    public static void setLoggerLevel(LoggerLevel LoggerLevel) {
        level = LoggerLevel.getValue();
    }

    public static MyLogger getLogger(String name) {

        return new LevelFilteredLogger(level,
                new ContextualLogger(name,
                        new CompositeLogger(new ConsoleLogger(),
                                new FileLogger("FileLogger.txt")
                        )
                )
        );
    }
}
