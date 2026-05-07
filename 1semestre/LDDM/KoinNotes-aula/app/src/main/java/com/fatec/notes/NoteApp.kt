package com.fatec.notes

import com.fatec.notes.di.appModule
import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

// classe que inicializa o koin
// permite a injeção automática de dependências como o repositório e o ViewModel
class NoteApp : Application() {
    override fun onCreate(){
        super.onCreate()
        startKoin {
            androidContext(this@NoteApp)
            modules(appModule)
        }
    }
}