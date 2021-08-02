package com.skylar.util.dbconn;

import java.sql.*;

public class OracleCloser {

    /**
     * @param rs
     * @param stmt
     * @param pstmt
     */
    public static void oracleCloseAll(ResultSet rs, Statement stmt, PreparedStatement pstmt) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {

            }
        }

        if (stmt != null) {
            try {
                stmt.close();
            } catch (Exception e) {

            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (Exception e) {

            }
        }
    }

    public static void oracleCloseAll(ResultSet rs, PreparedStatement pstmt) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {

            }

        }

        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (Exception e) {

            }
        }
    }

    public static void oracleCloseAll(ResultSet rs, Statement stmt) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {

            }
        }

        if (stmt != null) {
            try {
                stmt.close();
            } catch (Exception e) {

            }
        }

    }


}
