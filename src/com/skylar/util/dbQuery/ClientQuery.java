package com.exem.util.dbQuery;

/**
 * 쿼리문 모아두는 곳
 */
public class ClientQuery {

    public static final String SELECT_SQL_TEXT = "select NVL(SQL_ID, '') AS sqlId, \n " +
                                                "       NVL(PIECE, 1) AS piece, \n " +
                                                "       NVL(SQL_TEXT, '') AS sqlText " +
                                                " from v$sqltext where SQL_ID = ? ";

    public static final String SELECT_DB_STAT = "select NVL(STATISTIC#, 0) as statId, \n " +
                                                    "    NVL(value, 0) as value \n " +
                                                    "    from v$sysstat ";

    public static final String SELECT_SESSION_STAT = "SELECT\n" +
            "NVL(sid, 0) AS sId,\n" +
            "NVL(logon_time, sysdate) AS logonTime,\n" +
            "NVL(serial# , 0) AS serial,\n" +
            "NVL(status, 0) AS status,\n" +
            "NVL(taddr, '') AS taddr,\n" +
            "NVL(row_wait_file#, 0) AS rowWaitFile,\n" +
            "NVL(row_wait_block#, 0) AS rowWaitBlock,\n" +
            "NVL(row_wait_row#, 0) AS rowWaitRow,\n" +
            "NVL(row_wait_obj#, 0) AS rowWaitObject,\n" +
            "NVL(schemaname, '') AS schemaName,\n" +
            "NVL(module, '') AS module,\n" +
            "NVL(action, '') AS action,\n" +
            "NVL(client_info, '') AS clientInfo,\n" +
            "NVL(command, 0) AS commandType,\n" +
            "NVL(sql_address, '') AS sqlAddr,\n" +
            "NVL(sql_hash_value, 0) AS sqlHash,\n" +
            "NVL(sql_id, '') AS sqlId,\n" +
            "NVL(prev_sql_addr, '') AS prevSqlAddr,\n" +
            "NVL(prev_hash_value, 0) AS prevSqlHash,\n" +
            "NVL(prev_sql_id, '') AS prevSqlId\n" +
            "FROM    v$session\n";

    public static final String SELECT_SQL_STAT = "WITH sesstat AS (\n" +
            "  SELECT  sid,\n" +
            "          MAX ( CASE WHEN statistic# = 14   THEN value ELSE 0 END ) as logical_reads,\n" +
            "          MAX ( CASE WHEN statistic# = 148  THEN value ELSE 0 END ) as physical_reads,\n" +
            "          MAX ( CASE WHEN statistic# = 288  THEN value ELSE 0 END ) as redo_size,\n" +
            "          MAX ( CASE WHEN statistic# = 1718 THEN value ELSE 0 END ) as sort_disk,\n" +
            "          MAX ( CASE WHEN statistic# = 1719 THEN value ELSE 0 END ) as sort_rows,\n" +
            "          MAX ( CASE WHEN statistic# = 946  THEN value ELSE 0 END ) as table_fetch_by_rowid,\n" +
            "          MAX ( CASE WHEN statistic# = 947  THEN value ELSE 0 END ) as table_fetch_continued_by_rowid,\n" +
            "          MAX ( CASE WHEN statistic# = 1010  THEN value ELSE 0 END ) as table_scan_rows_gotten,\n" +
            "          MAX ( CASE WHEN statistic# = 1015  THEN value ELSE 0 END ) as table_scan_blocks_gotten\n" +
            "\n" +
            "  FROM v$sesstat\n" +
            "  GROUP BY sid\n" +
            ")\n" +
            "\n" +
            "SELECT  NVL(s.sql_address, '') AS sqlAddr,\n" +
            "        NVL(s.sql_hash_value, 0) AS sqlHash,\n" +
            "        NVL(s.sql_id, '') AS sqlId,\n" +
            "        NVL(t.plan_hash_value, 0) AS sqlPlanHash,\n" +
            "        NVL(s.username, '') AS userName,\n" +
            "        NVL(s.program, '') AS program,\n" +
            "        NVL(s.module, '') AS module,\n" +
            "        NVL(s.action, '') AS action,\n" +
            "        NVL(s.machine, '') AS machine,\n" +
            "        NVL(s.osuser, '') AS osUser,\n" +
            "        NVL(t.elapsed_time, 0) AS elapsedTime,\n" +
            "        NVL(t.cpu_time, 0) AS cpuTime,\n" +
            "        NVL(s.wait_time, 0) AS waitTime,\n" +
            "        NVL(s.logical_reads, 0) AS logicalReads,\n" +
            "        NVL(s.physical_reads, 0) AS physicalReads,\n" +
            "        NVL(s.redo_size, 0) AS redoSize,\n" +
            "        NVL(t.executions, 0) AS executionCount,\n" +
            "        NVL(s.sort_disk, 0) AS sortDisk,\n" +
            "        NVL(s.sort_rows, 0) AS sortRows,\n" +
            "        NVL(s.table_fetch_by_rowid, 0) AS tableFetchByRowId,\n" +
            "        NVL(s.table_fetch_continued_by_rowid, 0) AS tableFetchContinuedByRowId,\n" +
            "        NVL(s.table_scan_rows_gotten, 0) AS tableScanBlocksGotten ,\n" +
            "        NVL(s.table_scan_blocks_gotten, 0) AS tableScanRowsGotten\n" +
            "FROM (\n" +
            "  SELECT  ss.sql_address as sql_address,\n" +
            "          ss.sql_hash_value as sql_hash_value,\n" +
            "          ss.sql_id as sql_id,\n" +
            "          ss.username as username,\n" +
            "          ss.program as program,\n" +
            "          ss.module as module,\n" +
            "          ss.action as action,\n" +
            "          ss.machine as machine,\n" +
            "          ss.osuser as osuser,\n" +
            "          ss.wait_time as wait_time,\n" +
            "          st.logical_reads as logical_reads,\n" +
            "          st.physical_reads as physical_reads,\n" +
            "          st.redo_size as redo_size,\n" +
            "          st.sort_disk as sort_disk,\n" +
            "          st.sort_rows as sort_rows,\n" +
            "          st.table_fetch_by_rowid as table_fetch_by_rowid,\n" +
            "          st.table_fetch_continued_by_rowid as table_fetch_continued_by_rowid,\n" +
            "          st.table_scan_rows_gotten as table_scan_rows_gotten,\n" +
            "          st.table_scan_blocks_gotten as table_scan_blocks_gotten\n" +
            "  FROM    v$session ss, sesstat st\n" +
            "  WHERE   ss.sid = st.sid\n" +
            ") s, v$sqlstats t\n" +
            "WHERE s.sql_id = t.sql_id\n";


}
