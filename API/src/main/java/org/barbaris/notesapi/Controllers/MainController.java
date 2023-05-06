package org.barbaris.notesapi.Controllers;

import org.barbaris.notesapi.Models.UserModel;
import org.barbaris.notesapi.Services.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
                return new ResponseEntity<String>(HttpStatus.CREATED);
            case "Exists":
                return new ResponseEntity<String>(HttpStatus.IM_USED);
            case "DB Error":
            default:
                return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
