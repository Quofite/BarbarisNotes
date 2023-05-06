package org.barbaris.notesapi.Models;

import java.util.List;
import java.util.Map;

public interface DatabaseService {
    String register(UserModel user);
    String login(UserModel user);
    String saveNote(NoteModel note);
    List<Map<String, Object>> showAllNotes(UserModel user);
    String deleteNote(NoteModel note);
    String editNote(NoteModel note);
}
