package net.medrag.devBuilder.service;

import net.medrag.devBuilder.model.Request;
import net.medrag.schema.RaceType;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * {@author} Stanislav Tretyakov
 * 04.12.2019
 */
@Service
public class Repository {

    private static final String TABLE = "DEVELOPERS";
    private static final String ENDING = "ENDING";
    private static final String NAME = "NAME";
    private static final String SURNAME = "SURNAME";
    private static final String RACE = "RACE";
    private static final String AGE = "AGE";
    private static final String BDAY = "BDAY";
    private static final String SKILLS_COUNT = "SKILLS_COUNT";

    private static final String DB_URL = "jdbc:h2:file:./developers";
    private static final String INIT_SCRIPT = ";INIT=RUNSCRIPT FROM 'classpath:/db/create.sql'";
    private static final String ADD_NEW =
            String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s) VALUES (?,?,?,?,?,?,?)",
                    TABLE, ENDING, NAME, SURNAME, RACE, AGE, BDAY, SKILLS_COUNT);
    private static final String UPDATE_EXISTING =
            String.format("UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?",
                    TABLE, NAME, SURNAME, RACE, AGE, BDAY, SKILLS_COUNT, ENDING);
    private static final String GET_BY_ENDING = String.format("SELECT * FROM %s WHERE %s = ?", TABLE, ENDING);
    private static final String GET_ALL = String.format("SELECT * FROM %s", TABLE);
    private static final String REMOVE_BY_ENDING = String.format("DELETE FROM %s WHERE %s = ?", TABLE, ENDING);

    /**
     * Initializes database
     */
    @PostConstruct
    void initDatabase() {
        try {
            DriverManager.registerDriver(new org.h2.Driver());
            Connection connection = DriverManager.getConnection(DB_URL + INIT_SCRIPT);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns RaceType by string value
     *
     * @param val - string value
     * @return RaceType.valueOf(val) or random value if incorrect
     */
    private RaceType getRace(String val) {
        try {
            return RaceType.valueOf(val);
        } catch (IllegalArgumentException | NullPointerException e) {
            return RaceType.values()[new Random().nextInt(RaceType.values().length)];
        }
    }

    /**
     * Build request object from result set
     *
     * @param rs - result set
     * @return - request object
     * @throws SQLException - if extracted data is incorrect
     */
    private Request buildRequest(ResultSet rs) throws SQLException {
        return Request.builder().key(rs.getString(ENDING))
                .age(rs.getInt(AGE)).bday(rs.getString(BDAY))
                .name(rs.getString(NAME)).surname(rs.getString(SURNAME))
                .skillsCount(rs.getInt(SKILLS_COUNT)).status("DONE")
                .race(getRace(rs.getString(RACE)).value()).build();
    }

    /**
     * Get request entry by id
     *
     * @param ending - id
     * @return requested entry
     */
    public Request getById(String ending) {
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement statement = connection.prepareStatement(GET_BY_ENDING)) {
            statement.setString(1, ending);
            ResultSet rs = statement.executeQuery();
            return rs.next() ? buildRequest(rs) : null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Save or update request entry in database
     *
     * @param request - entry to save or update
     * @return - persisted entry
     */
    public Request save(Request request) {
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement insert = connection.prepareStatement(ADD_NEW);
             PreparedStatement update = connection.prepareStatement(UPDATE_EXISTING);
             PreparedStatement check = connection.prepareStatement(GET_BY_ENDING)) {
            check.setString(1, request.getKey());
            ResultSet resultSet = check.executeQuery();
            if (resultSet.next()) {
                update.setString(1, request.getName());
                update.setString(2, request.getSurname());
                update.setString(3, request.getRace());
                update.setInt(4, request.getAge());
                update.setString(5, request.getBday());
                update.setInt(6, request.getSkillsCount());
                update.setString(7, request.getKey());
                update.executeUpdate();
            } else {
                insert.setString(1, request.getKey());
                insert.setString(2, request.getName());
                insert.setString(3, request.getSurname());
                insert.setString(4, request.getRace());
                insert.setInt(5, request.getAge());
                insert.setString(6, request.getBday());
                insert.setInt(7, request.getSkillsCount());
                insert.executeUpdate();
            }
            return request;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Remove developer entry from database
     *
     * @param ending - id
     * @return - removed request entry
     */
    public Request remove(String ending) {
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement delete = connection.prepareStatement(REMOVE_BY_ENDING)) {
            delete.setString(1, ending);
            delete.executeUpdate();
            Request request = new Request();
            request.setKey(ending);
            request.setStatus("REMOVED");
            return request;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get all request entries from database
     *
     * @return Map<Id, Request>
     */
    public Map<String, Request> getAll() {
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement getAll = connection.prepareStatement(GET_ALL)) {
            ResultSet rs = getAll.executeQuery();
            Map<String, Request> all = new HashMap<>();
            while (rs.next()) {
                all.put(rs.getString(ENDING), buildRequest(rs));
            }
            return all;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
