package com.test.sec.service.impl;

import com.test.sec.model.Note;
import com.test.sec.repository.NoteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import com.test.sec.service.NoteService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Override
    @Transactional
    public Note createNoteForUser(String username, String content) {
        Note note = new Note();
        note.setContent(content);
        note.setOwnerUsername(username);
        Note savedNote = noteRepository.save(note);
        return savedNote;
    }

    @Override
    public Note updateNoteForUser(Long noteId, String content, String username) {
        Note note = noteRepository.findById(noteId).orElseThrow(()
                -> new RuntimeException("Note not found"));
        note.setContent(content);
        Note updatedNote = noteRepository.save(note);
        return updatedNote;
    }

    @Override
    public void deleteNoteForUser(Long noteId, String username) {
        noteRepository.deleteById(noteId);
    }

    @Override
    public List<Note> getNotesForUser(String username) {
        List<Note> personalNotes = noteRepository
                .findByOwnerUsername(username);
        return personalNotes;
    }

    @Override
    public Note getNoteById(Long noteId) {
        Note note = noteRepository.findById(noteId).orElseThrow(()
                -> new RuntimeException("Note not found"));
        return note;
    }
}
