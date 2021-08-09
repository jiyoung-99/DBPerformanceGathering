package com.exem.util.dbQuery;

public class ServerQuery {


    public static final String SELECT_DB_ALARM_HISTORY = "select count(1) as count " +
                                                            "from ora_db_alarm_history\n" +
                                                            "where stat_id = ?\n " +
                                                            "and alert_type = ?\n " +
                                                            "and time between cast(? as timestamp) and cast(? as timestamp) + interval '1 days' ";

    public static final String INSERT_DB_ALARM_HISTORY = "insert into ora_db_alarm_history " +
                                                                    "(partition_key, " +
                                                                    "db_id, " +
                                                                    "time, " +
                                                                    "alert_type, " +
                                                                    "stat_id, " +
                                                                    "value) " +
                                                            "values ( " +
                                                                    "?, " +
                                                                    "?, " +
                                                                    "to_timestamp(?, 'YYYY-MM-DD HH24:MI:SS'), " +
                                                                    "?, " +
                                                                    "?, " +
                                                                    "? " +
                                                                    ") ";

    public static final String INSERT_DB_STAT_10MIN  ="select  insert_ora_db_stat_10min(cast(? as timestamp)) ";

    public static final String INSERT_DB_STAT_1HOUR  ="select  insert_ora_db_stat_1hour(cast(? as timestamp)) ";

    public static final String INSERT_DB_STAT = "INSERT INTO ORA_DB_STAT(\n" +
                                                                    "    PARTITION_KEY ,\n" +
                                                                    "   DB_ID ,\n" +
                                                                    "   TIME ,\n" +
                                                                    "   STAT_ID ,\n" +
                                                                    "   AVG_VALUE, \n" +
                                                                    "   MIN_VALUE, \n" +
                                                                    "   MAX_VALUE \n" +
                                                                    ")\n" +
                                                                    "values(?,\n" +
                                                                    "       ?,\n" +
                                                                    "       to_timestamp(?, 'YYYY-MM-DD HH24:MI:SS'),\n" +
                                                                    "       ?,\n" +
                                                                    "       ?,\n" +
                                                                    "       ?,\n" +
                                                                    "       ?\n" +
                                                                    "      )";

    public static final String INSERT_SESSION_STAT = "INSERT INTO ORA_SESSION_STAT (\n" +
                                                        "        PARTITION_KEY   ,       \n" +
                                                        "           DB_ID           ,      \n" +
                                                        "           TIME            ,      \n" +
                                                        "           SID             ,      \n" +
                                                        "           LOGON_TIME      ,      \n" +
                                                        "           SERIAL          , \n" +
                                                        "           STATUS          ,      \n" +
                                                        "           TADDR           ,      \n" +
                                                        "           ROW_WAIT_FILE   ,      \n" +
                                                        "           ROW_WAIT_BLOCK  ,      \n" +
                                                        "           ROW_WAIT_ROW    ,      \n" +
                                                        "           ROW_WAIT_OBJECT ,      \n" +
                                                        "           SCHEMA_NAME     ,     \n" +
                                                        "           MODULE          ,      \n" +
                                                        "           ACTION          ,      \n" +
                                                        "           CLIENT_INFO     ,       \n" +
                                                        "           COMMAND_TYPE    ,      \n" +
                                                        "           SQL_ADDR        ,      \n" +
                                                        "           SQL_HASH        ,      \n" +
                                                        "           SQL_ID          ,      \n" +
                                                        "           PREV_SQL_ADDR   ,      \n" +
                                                        "           PREV_SQL_HASH   ,      \n" +
                                                        "           PREV_SQL_ID     \n" +
                                                        ")VALUES(\n" +
                                                        "    ?,\n" +
                                                        "    ?,\n" +
                                                        "        to_timestamp(?, 'YYYY-MM-DD HH24:MI:SS'),\n" +
                                                        "        ?,\n" +
                                                        "        to_timestamp(?, 'YYYY-MM-DD HH24:MI:SS'),\n" +
                                                        "        ?,\n" +
                                                        "        ?,\n" +
                                                        "        ?,\n" +
                                                        "        ?,\n" +
                                                        "        ?,\n" +
                                                        "        ?,\n" +
                                                        "        ?,\n" +
                                                        "        ?,\n" +
                                                        "        ?,\n" +
                                                        "        ?,\n" +
                                                        "        ?,\n" +
                                                        "        ?,\n" +
                                                        "        ?,\n" +
                                                        "        ?,\n" +
                                                        "        ?,\n" +
                                                        "        ?,\n" +
                                                        "        ?,\n" +
                                                        "        ?)";
    public static final String INSERT_SQL_STAT = "INSERT INTO ORA_SQL_STAT (\n" +
                                                " PARTITION_KEY                  ,  \n" +
                                            "     DB_ID                          ,  \n" +
                                            "     TIME                           , \n" +
                                            "     SQL_ADDR                       ,  \n" +
                                            "     SQL_HASH                       ,  \n" +
                                            "     SQL_ID                         ,  \n" +
                                            "     SQL_PLAN_HASH                  ,  \n" +
                                            "     USER_NAME                      ,       \n" +
                                            "     PROGRAM                        ,       \n" +
                                            "     MODULE                         ,  \n" +
                                            "     ACTION                         ,  \n" +
                                            "     MACHINE                        ,       \n" +
                                            "     OS_USER                        ,       \n" +
                                            "     ELAPSED_TIME                   ,  \n" +
                                            "     CPU_TIME                       ,  \n" +
                                            "     WAIT_TIME                      ,  \n" +
                                            "     LOGICAL_READS                  ,  \n" +
                                            "     PHYSICAL_READS                 ,  \n" +
                                            "     REDO_SIZE                      ,  \n" +
                                            "     EXECUTION_COUNT                ,  \n" +
                                            "     SORT_DISK                      ,  \n" +
                                            "     SORT_ROWS                      ,  \n" +
                                            "     TABLE_FETCH_BY_ROWID           ,  \n" +
                                            "     TABLE_FETCH_CONTINUED_BY_ROWID ,  \n" +
                                            "     TABLE_SCAN_BLOCKS_GOTTEN       ,  \n" +
                                            "     TABLE_SCAN_ROWS_GOTTEN          \t\n" +
                                            ")VALUES (\n" +
                                            "         ?,\n" +
                                            "         ?,\n" +
                                            "         to_timestamp(?, 'YYYY-MM-DD HH24:MI:SS'),\n" +
                                            "         ?,\n" +
                                            "         ?,\n" +
                                            "         ?,\n" +
                                            "         ?,\n" +
                                            "         ?,\n" +
                                            "         ?,\n" +
                                            "         ?,\n" +
                                            "         ?,\n" +
                                            "         ?,\n" +
                                            "         ?,\n" +
                                            "         ?,\n" +
                                            "         ?,\n" +
                                            "         ?,\n" +
                                            "         ?,\n" +
                                            "         ?,\n" +
                                            "         ?,\n" +
                                            "         ?,\n" +
                                            "         ?,\n" +
                                            "         ?,\n" +
                                            "         ?,\n" +
                                            "         ?,\n" +
                                            "         ?,\n" +
                                            "         ?\n" +
                                            "        )";


    public static final String INSERT_SQL_TEXT = "INSERT INTO ORA_SQL_TEXT(\n" +
                                                "                         PARTITION_KEY, " +
                                                "                         SQL_ID,\n" +
                                                "                         PIECE,\n" +
                                                "                         SQL_TEXT,\n" +
                                                "                         TIME\n" +
                                                "                        )\n" +
                                                "                        VALUES (?,\n " +
                                                "                                ?,\n " +
                                                "                                ?,\n " +
                                                "                                ?,\n " +
                                                "                                to_timestamp(?, 'YYYY-MM-DD HH24:MI:SS') \n " +
                                                "                               ) ";
}
