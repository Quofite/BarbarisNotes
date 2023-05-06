package org.barbaris.notesapi.Services;

import org.barbaris.notesapi.Models.NoteModel;
import org.barbaris.notesapi.Models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public String login(UserModel user) {
        String sql = String.format("SELECT * FROM notesusers WHERE login='%s' AND password='%s';",
                user.getLogin(), user.getPassword());
        try {
            List<Map<String, Object>> rows = template.queryForList(sql);

            if(rows.size() == 1) {
                return "OK";
            } else {
                return "No user";
            }
        } catch (Exception ex) {
            return "DB Error";
        }
    }

    public String saveNote(NoteModel note) {
        String response = login(note.getUser());
        if(response.equals("OK")) {
            String sql = String.format("INSERT INTO notes(login, note, id) VALUES('%s', '%s', %d);",
                    note.getUser().getLogin(), note.getNote(), getLastNoteId(note.getUser()) + 1);

            try {
                template.execute(sql);
            } catch (Exception ex) {
                return "DB Error";
            }

            return "OK";
        } else if(response.equals("No user")) {
            return "No user";
        } else {
            return "DB Error";
        }
    }

    public List<Map<String, Object>> showAllNotes(UserModel user) {
        String response = login(user);

        if(response.equals("OK")) {
            String sql = String.format("SELECT * FROM notes WHERE login='%s';", user.getLogin());

            try {
                return template.queryForList(sql);
            } catch (Exception ex) {
                List<Map<String, Object>> err = new ArrayList<>();
                Map<String, Object> map = new HashMap<>();
                map.put("Error", "DB Error");
                err.add(map);
                return err;
            }

        } else if(response.equals("DB Error")) {
            List<Map<String, Object>> err = new ArrayList<>();
            Map<String, Object> map = new HashMap<>();
            map.put("Error", "DB Error");
            err.add(map);
            return err;
        } else {
            List<Map<String, Object>> err = new ArrayList<>();
            Map<String, Object> map = new HashMap<>();
            map.put("Error", "No user");
            err.add(map);
            return err;
        }
    }

    public String deleteNote(NoteModel note) {
        String response = login(note.getUser());

        if(response.equals("OK")) {
            String sql = String.format("DELETE FROM notes WHERE login='%s' AND id=%d;",
                    note.getUser().getLogin(), note.getId());

            try {
                template.execute(sql);
                return "OK";

            } catch (Exception ex) {
                return "DB Error";
            }

        } else {
            return response;
        }
    }

    public String editNote(NoteModel note) {
        String response = login(note.getUser());

        if(response.equals("OK")) {
            String sql = String.format("UPDATE notes SET note='%s' WHERE id=%d",
                    note.getNote(), note.getId());

            try {
                template.execute(sql);
                return "OK";

            } catch (Exception ex) {
                return "DB Error";
            }

        } else {
            return response;
        }
    }

    // ---------------

    private int getLastNoteId(UserModel user) {
        String response = login(user);

        if(response.equals("OK")) {
            String sql = String.format("SELECT id FROM notes WHERE login='%s';", user.getLogin());

            try {
                List<Map<String, Object>> rows = template.queryForList(sql);

                if(rows.size() == 0) {
                    return 0;
                } else {
                    return (int) rows.get(rows.size() - 1).get("id");
                }
            } catch (Exception ex) {
                return 0;
            }

        } else {
            return 0;
        }
    }
}









