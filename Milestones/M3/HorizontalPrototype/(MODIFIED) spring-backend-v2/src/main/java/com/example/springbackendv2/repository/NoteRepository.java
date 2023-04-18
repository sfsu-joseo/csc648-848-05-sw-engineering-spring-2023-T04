package com.example.springbackendv2.repository;

import com.example.springbackendv2.model.Note;
import com.example.springbackendv2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {

    @Query("select n from Note n WHERE n.user_id = :userId")
    List<Note> findAllByUserId(Long userId);

    @Query("select n from Note n WHERE n.user_id = :userId ODER BY id DESC LIMIT 1")
    Note findLatestNoteByUserId(Long userId);

    @Query("update Note n set n.text = :text WHERE n.id = :id")
    void updateNoteTextById(Long id, String text);
}
