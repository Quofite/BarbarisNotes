package org.barbaris.notesapi.Services;

import org.barbaris.notesapi.Models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DatabaseService {
    @Autowired
    public JdbcTemplate template;

    public String register(UserModel user) {
        String sql = String.format("SELECT * FROM notesusers WHERE login='%s';", user.getLogin());
        List<Map<String, Object>> rows = template.queryForList(sql);

        if(rows.size() > 0) {
            return "Exists";
        }

        sql = String.format("INSERT INTO notesusers(login, password, email) VALUES('%s', '%s', '%s')",
                user.getLogin(), user.getPassword(), user.getEmail());

        try {
            template.execute(sql);
        } catch (Exception ex) {
            return "DB Error";
        }

        return "CREATED";
    }
}
