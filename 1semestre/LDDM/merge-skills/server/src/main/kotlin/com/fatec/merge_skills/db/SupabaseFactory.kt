package com.fatec.merge_skills.db

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

object SupabaseFactory {
    val client: SupabaseClient by lazy{
        createSupabaseClient(
            supabaseUrl = System.getenv("SUPABASE_URL") ?: "",
            supabaseKey = System.getenv("SUPABASE_KEY") ?: ""
        ){
            install(Postgrest)
        }
    }
}