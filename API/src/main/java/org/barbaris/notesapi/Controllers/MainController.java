package org.barbaris.notesapi.Controllers;

import org.barbaris.notesapi.Models.NoteModel;
import org.barbaris.notesapi.Models.UserModel;
import org.barbaris.notesapi.Services.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@Controller
public class MainController {
    private final DatabaseService service;

    @Autowired
    public MainController(DatabaseService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserModel user) {
        String response = service.register(user);

        switch (response) {
            case "CREATED":
                return new ResponseEntity<>("Registered", HttpStatus.CREATED);
            case "Exists":
                return new ResponseEntity<>("Login is already in use", HttpStatus.IM_USED);
            case "DB Error":
            default:
                return new ResponseEntity<>("Database error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserModel user) {
        String response = service.login(user);

        if(response.equals("OK")) {
            return new ResponseEntity<>("Logined", HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>("Wrong login or password", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveNote(@RequestBody NoteModel note) {
        String response = service.saveNote(note);

        if(response.equals("OK")) {
            return new ResponseEntity<>("Note saved", HttpStatus.CREATED);
        } else if (response.equals("DB Error")) {
            return new ResponseEntity<>("Database error", HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return new ResponseEntity<>("Wrong login or password", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/notes")
    public ResponseEntity<?> getAllNotes(@RequestBody UserModel user) {
        List<Map<String, Object>> notes = service.showAllNotes(user);
        if(notes.size() == 0) {
            return new ResponseEntity<>("No notes", HttpStatus.OK);
        } else {
            if(notes.get(0).get("Error") != null) {
                return new ResponseEntity<>(notes.get(0).get("Error"), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(notes, HttpStatus.OK);
            }
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteNote(@RequestBody NoteModel note) {
        String response = service.deleteNote(note);

        if(response.equals("OK")) {
            return new ResponseEntity<>("Note deleted", HttpStatus.OK);
        } else if (response.equals("DB Error")) {
            return new ResponseEntity<>("Database error", HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return new ResponseEntity<>("Wrong login or password", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/edit")
    public ResponseEntity<?> editNote(@RequestBody NoteModel note) {
        String response = service.editNote(note);

        if(response.equals("OK")) {
            return new ResponseEntity<>("Note edited", HttpStatus.OK);
        } else if (response.equals("DB Error")) {
            return new ResponseEntity<>("Database error", HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return new ResponseEntity<>("Wrong login or password", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteuser")
    public ResponseEntity<?> deleteUser(@RequestBody UserModel user) {
        String response = service.deleteUser(user);

        if(response.equals("OK")) {
            return new ResponseEntity<>("User deleted", HttpStatus.OK);
        } else if (response.equals("DB Error")) {
            return new ResponseEntity<>("Database error", HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return new ResponseEntity<>("Wrong login or password", HttpStatus.NOT_FOUND);
        }
    }
}






























