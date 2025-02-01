package com.foxtail.NotesBackend.controllers;

import com.foxtail.NotesBackend.models.Note;
import com.foxtail.NotesBackend.models.Users;
import com.foxtail.NotesBackend.repositories.NoteRepository;
import com.foxtail.NotesBackend.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    public NoteController(NoteRepository noteRepository, UserRepository userRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }

    // Create a note
    @PostMapping("/create/{userId}")
    public ResponseEntity<Note> createNote(@PathVariable Long userId, @RequestBody Note noteRequest) {
        Optional<Users> user = userRepository.findById(userId);
        if (user.isPresent()) {
            noteRequest.setUser(user.get());
            Note savedNote = noteRepository.save(noteRequest);
            return ResponseEntity.ok(savedNote);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Get all notes for a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Note>> getAllNotesForUser(@PathVariable Long userId) {
        Optional<Users> user = userRepository.findById(userId);
        return user.map(value -> ResponseEntity.ok(noteRepository.findByUser(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get a single note
    @GetMapping("/{noteId}")
    public ResponseEntity<Note> getNoteById(@PathVariable Long noteId) {
        return noteRepository.findById(noteId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update a note
    @PutMapping("/{noteId}")
    public ResponseEntity<Note> updateNote(@PathVariable Long noteId, @RequestBody Note noteRequest) {
        return noteRepository.findById(noteId).map(note -> {
            note.setTitle(noteRequest.getTitle());
            note.setContent(noteRequest.getContent());
            return ResponseEntity.ok(noteRepository.save(note));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Soft delete a note
//    @DeleteMapping("/{noteId}")
//    public ResponseEntity<Void> softDeleteNote(@PathVariable Long noteId) {
//        return noteRepository.findById(noteId).map(note -> {
//            note.setDeleted(true);
//            noteRepository.save(note);
//            return ResponseEntity.ok().build();
//        }).orElseGet(() -> ResponseEntity.notFound().build());
//    }

}
