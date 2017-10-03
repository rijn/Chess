package chessGame.model.storage;
import java.sql.*;

public class DB {
    static Connection c = null;

    public static void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:chess.db");
            c.setAutoCommit(false);

            initialize();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public static void disconnect() {
        try {
            c.close();
        } catch ( Exception e ) {}
    }

    public static void initialize() {
        Statement stmt = null;
        try {
            stmt = c.createStatement();

            String sql;

            sql = "CREATE TABLE IF NOT EXISTS users " +
                    "(id   INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    " name TEXT                              NOT NULL)";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS games " +
                    "(id       INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    " white_id INTEGER                           NOT NULL," +
                    " black_id INTEGER                           NOT NULL," +
                    " FOREIGN KEY (white_id) REFERENCES users(id)," +
                    " FOREIGN KEY (black_id) REFERENCES users(id) )";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS moves " +
                    "(id        INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    " game_id   INTEGER                           NOT NULL," +
                    " player_id INTEGER                           NOT NULL," +
                    " from_x    INTEGER                                   ," +
                    " from_y    INTEGER                                   ," +
                    " to_x      INTEGER                                   ," +
                    " to_y      INTEGER                                   ," +
                    " comment   INTEGER                                   ," +
                    " FOREIGN KEY (game_id)   REFERENCES games(id)," +
                    " FOREIGN KEY (player_id) REFERENCES users(id) )";
            stmt.executeUpdate(sql);

            stmt.close();
            c.commit();
        } catch ( Exception e ) { }
    }

    public static Boolean isUserExist(String name) throws Exception {
        Statement stmt = c.createStatement();
        Boolean result = stmt.executeQuery( "SELECT COUNT(*) as count FROM users WHERE `name` = '" + name + "';")
                .getInt("count") != 0;
        stmt.close();
        return result;
    }

    public static Boolean registerUser(String name) {
        Statement stmt = null;
        try {
            if ( isUserExist(name) ) {
                return false;
            }

            stmt = c.createStatement();
            stmt.executeUpdate("INSERT INTO users (name) VALUES ('" + name + "');");
            stmt.close();
            c.commit();
        } catch ( Exception e ) { }

        return true;
    }

    public static Integer getUserId(String name) {
        Integer id = 0;
        Statement stmt = null;
        try {
            if ( !isUserExist(name) ) {
                return 0;
            }

            stmt = c.createStatement();
            id = stmt.executeQuery( "SELECT id FROM users WHERE `name` = '" + name + "';" ).getInt("id");
            stmt.close();
        } catch ( Exception e ) { }

        return id;
    }
}