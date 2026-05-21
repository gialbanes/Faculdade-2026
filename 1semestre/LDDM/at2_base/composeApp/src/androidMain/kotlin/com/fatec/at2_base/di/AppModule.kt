package com.fatec.at2_base.di

import com.fatec.at2_base.data.InMemoryMovieRepository
import com.fatec.at2_base.data.MovieRepository
import com.fatec.at2_base.viewmodel.MovieViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    singleOf(::InMemoryMovieRepository) bind MovieRepository::class

    viewModelOf(::MovieViewModel)
}