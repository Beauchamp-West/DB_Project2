import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Account {
    private static boolean generateAccount;
    static Connection con = null;
    static Statement statement;

    public static void setConnection(Connection conn) throws SQLException {
        con = conn;
        statement = con.createStatement();
    }

    public static void createUser(String name, String password) throws SQLException {
        //创建普通用户
        String create = "create user " + name + " password " + password + ";";
        statement.execute(create);
    }

    public static void createManager(String name, String password, String privileges) throws SQLException {
        //创建管理员
        //多种权限需由“ ”空格分隔
        String create = "create role " + name + " " + privileges + " password " + password + " login;";
        statement.execute(create);
    }

    public static void grantPrivilegesOnDatabase(String name, String privileges, String database) throws SQLException {
        //授予用户某一数据库权限
        //多种权限需由“ ”空格分隔
        String grant = "grant " + privileges + " on database " + database + " to " + name + ";";
        statement.execute(grant);
    }

    public static void grantPrivilegesOnTable(String name, String privileges, String table, String schema) throws SQLException {
        //授予用户某表（多个表）权限
        //多种权限需由“ ”空格分隔
        String grant = "grant " + privileges + " on " + table + " in schema " + schema + " to " + name + ";";
        statement.execute(grant);
    }

    public static void grantPrivilegesOnSchema(String name, String privileges, String schema) throws SQLException {
        //授予用户某表（多个表）权限
        //多种权限需由“ ”空格分隔
        String grant = "grant " + privileges + " on schema " + schema + " to " + name + ";";
        statement.execute(grant);
    }

    public static void revokePrivilegesOnDatabase(String name, String privileges, String database) throws SQLException {
        String revoke = "revoke " + privileges + " on database " + database + "from" + name + ";";
        statement.execute(revoke);
    }

    public static void revokePrivilegesOnTable(String name, String privileges, String table, String schema) throws SQLException {
        String revoke = "revoke " + privileges + " on " + table + " in schema " + schema + "from" + name + ";";
        statement.execute(revoke);
    }

    public static void revokePrivilegesOnSchema(String name, String privileges, String schema) throws SQLException {
        String revoke = "revoke " + privileges + " on schema " + schema + "from" + name + ";";
        statement.execute(revoke);
    }

    public static void dropUser(String user) throws SQLException {
        //make sure you have revoked all privileges from user
        String drop = "drop user "+user+";";
        statement.execute(drop);
    }

    public static void dropUserForcibly(String user) throws SQLException {
        //drop without revoked all privileges
        //NOT RECOMMENDED
        String drop = "drop owned by "+user+";";
        statement.execute(drop);
        drop = "drop user "+user+";";
        statement.execute(drop);
    }
}
