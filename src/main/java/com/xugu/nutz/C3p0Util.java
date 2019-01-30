package com.xugu.nutz;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3p0Util {
	private static DataSource ds = new ComboPooledDataSource();

    public static DataSource getDataSource() {
        return ds;
    }

    /**
     * ���ڴӳ��л�ȡ����
     * 
     * @return
     */
    public static synchronized Connection getConneciton() {
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // �ر���Դ
    public static void release(ResultSet rs, Statement st, Connection con) {
        try {
            if (rs != null) {
                rs.close();
                rs = null;// Ŀ�����û���������������������
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (st != null) {
                st.close();
                st = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
