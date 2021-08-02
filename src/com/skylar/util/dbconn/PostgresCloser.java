package com.skylar.util.dbconn;

import java.sql.*;

public class PostgresCloser {


    public static void postgresCloseAll(ResultSet rs, Statement stmt, PreparedStatement pstmt) {
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

    public static void postgresCloseAll(ResultSet rs, PreparedStatement pstmt) {
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

    public static void postgresCloseAll(ResultSet rs, CallableStatement cstmt) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {

            }
        }
        if (cstmt != null) {
            try {
                cstmt.close();
            } catch (Exception e) {

            }
        }

    }


}
