package com.fatec.notes.data

import com.fatec.notes.model.Note

/**
 * Interface do Repository
 *
 * Define O QUE o repositório faz e não COMO
 */

interface NoteRepository {
    fun getAll() : List<Note>
    fun add(note: Note)
    fun remove(noteId: Long)
}