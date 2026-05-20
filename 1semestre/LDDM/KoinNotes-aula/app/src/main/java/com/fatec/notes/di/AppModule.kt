package com.fatec.notes.di

import com.fatec.notes.data.InMemoryNoteRepository
import com.fatec.notes.data.NoteRepository
import com.fatec.notes.viewmodel.NoteViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import com.fatec.notes.data.QuestionRepository
import com.fatec.notes.data.InMemoryQuestionRepository
import com.fatec.notes.viewmodel.QuestionFormViewModel
import org.koin.dsl.module

// configuração de dependências do koin
val appModule = module {
    // InMemoryNoteRepository como implementação de NoteRepository
    singleOf(::InMemoryNoteRepository) bind NoteRepository::class

    // NoteViewModel para ser criado pelo Koin
    viewModelOf(::NoteViewModel)

    singleOf(::InMemoryQuestionRepository) bind QuestionRepository::class
    viewModelOf(::QuestionFormViewModel)
}