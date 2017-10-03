package chessGame.model.storage;
import chessGame.controller.EventController;
import chessGame.controller.GameController;
import chessGame.controller.MoveController;
import chessGame.model.Player;

import java.sql.*;
import java.util.List;

/**
 * Database model
 */
public class DB {
    /**
     * Hold overall connection
     */
    static Connection c = null;

    /**
     * connect to the db
     */
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

    /**
     * disconnect from the db
     */
    public static void disconnect() {
        try {
            c.close();
        } catch ( Exception e ) {}
    }

    /**
     * Initialize the db. create table if not exists
     */
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

    /**
     * Test if given user exists
     * @param name
     * @return
     * @throws Exception
     */
    public static Boolean isUserExist(String name) throws Exception {
        Statement stmt = c.createStatement();
        Boolean result = stmt.executeQuery( "SELECT COUNT(*) as count FROM users WHERE `name` = '" + name + "';")
                .getInt("count") != 0;
        stmt.close();
        return result;
    }

    /**
     * Register user to the db
     * @param name
     * @return
     */
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

    /**
     * Get user id from the db
     * @param name
     * @return
     */
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

    /**
     * Insert game record into the db
     * @param players
     * @return
     */
    public static Long newGame(List<Player> players) {
        Statement stmt = null;
        try {
            stmt = c.createStatement();
            stmt.executeUpdate("INSERT INTO games (white_id, black_id) VALUES (" + players.get(0).id + "," + players.get(1).id + ");");
            stmt.close();
            c.commit();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch ( Exception e ) { }

        return Long.valueOf(0);
    }

    /**
     * Insert move record to the db
     * @param gc
     * @param mc
     * @return
     */
    public static Long insertMove(GameController gc, MoveController mc) {
        Statement stmt = null;
        try {
            stmt = c.createStatement();
            stmt.executeUpdate("INSERT INTO moves (game_id, player_id, from_x, from_y, to_x, to_y, comment)" +
                    " VALUES (" + gc.getId() + "," + mc.getPlayer().id + "," +
                    mc.getFrom().x + "," + mc.getFrom().y + "," +
                    mc.getTo().x + "," + mc.getTo().y + "," +
                    "'" + mc.getSource().name + "');");
            stmt.close();
            c.commit();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }

        return Long.valueOf(0);
    }

    /**
     * Remove move record from the db
     * @param id
     */
    public static void removeMove(Long id) {
        Statement stmt = null;
        try {
            stmt = c.createStatement();
            stmt.executeUpdate("DELETE FROM moves WHERE id = " + id + " LIMIT 1;");
            stmt.close();
            c.commit();
        } catch ( Exception e ) { }
    }

    /**
     * Insert a particular event into the db
     * @param gc
     * @param ec
     */
    public static void insertEvent(GameController gc, EventController ec) {
        Statement stmt = null;
        try {
            stmt = c.createStatement();
            stmt.executeUpdate("INSERT INTO moves (game_id, player_id, comment)" +
                    " VALUES (" + gc.getId() + "," + ec.getPlayer().id + "," +
                    "'" + ec.getEvent() + "');");
            stmt.close();
            c.commit();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
}