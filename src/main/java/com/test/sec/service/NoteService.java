package com.test.sec.service;

import com.test.sec.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NoteService {
    Note createNoteForUser(String username, String content , String title);

    Note updateNoteForUser(Long noteId, String content, String title, String username);

    void deleteNoteForUser(Long noteId, String username);

    List<Note> getNotesForUser(String username);

    Note getNoteById(Long noteId);
}
