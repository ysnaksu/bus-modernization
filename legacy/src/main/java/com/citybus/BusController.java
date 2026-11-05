package com.citybus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

// Everything lives in one class: HTTP handling, validation and database access.
@RestController
public class BusController {

    @Autowired
    private JdbcTemplate jdbc;

    private RowMapper<Route> mapper = new RowMapper<Route>() {
        public Route mapRow(ResultSet rs, int rowNum) throws SQLException {
            Route r = new Route();
            r.setId(rs.getInt("id"));
            r.setName(rs.getString("name"));
            r.setStartStop(rs.getString("start_stop"));
            r.setEndStop(rs.getString("end_stop"));
            return r;
        }
    };

    @RequestMapping(value = "/routes", method = RequestMethod.GET)
    public List<Route> list() {
        System.out.println("Listing all routes");
        return jdbc.query("SELECT * FROM routes", mapper);
    }

    @RequestMapping(value = "/routes/{id}", method = RequestMethod.GET)
    public Route get(@PathVariable("id") int id) {
        System.out.println("Getting route " + id);
        List<Route> result = jdbc.query("SELECT * FROM routes WHERE id = " + id, mapper);
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

    @RequestMapping(value = "/routes", method = RequestMethod.POST)
    public String add(@RequestBody Route route) {
        if (StringUtils.isBlank(route.getName())) {
            return "ERROR: name is required";
        }
        jdbc.update("INSERT INTO routes (name, start_stop, end_stop) VALUES ('"
                + route.getName() + "', '"
                + route.getStartStop() + "', '"
                + route.getEndStop() + "')");
        System.out.println("Added route " + route.getName());
        return "OK";
    }
}
