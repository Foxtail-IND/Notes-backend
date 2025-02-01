package com.foxtail.NotesBackend.repositories;

import com.foxtail.NotesBackend.models.Note;
import com.foxtail.NotesBackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByUser(User user);
}
