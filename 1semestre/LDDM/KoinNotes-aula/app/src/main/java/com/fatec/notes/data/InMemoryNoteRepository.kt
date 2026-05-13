package com.fatec.notes.data

import com.fatec.notes.model.Note

// implementação do repository
// guarda as notas em memória
// seu eu quiser rodar em um banco real, é so alterar essa classe
class InMemoryNoteRepository : NoteRepository {
    private val notes = mutableListOf<Note>()

    override fun getAll(): List<Note> = notes.toList()

    override fun add(note: Note) {
        notes.add(note)
    }

    override fun remove(noteId: Long) {
        notes.removeAll { it.id == noteId }
    }
}