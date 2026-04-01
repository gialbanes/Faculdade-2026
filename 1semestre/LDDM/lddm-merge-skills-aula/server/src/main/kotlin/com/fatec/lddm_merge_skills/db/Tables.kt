package com.fatec.lddm_merge_skills.db

import kotlinx.serialization.json.Json
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.json.jsonb

object Courses : IntIdTable("courses") {
    val title = text("title")
    val description = text("description").nullable()
    val icon = text("icon").nullable()
    val color = text("color").nullable()
    val totalLessons = integer("total_lessons").nullable().default(0)
}

object Lessons : IntIdTable("lessons") {
    val courseId = reference("course_id", Courses, onDelete = ReferenceOption.CASCADE)
    val title = text("title")
    val description = text("description").nullable()
    val order = integer("order").nullable()
}

object Questions : IntIdTable("questions") {
    val lessonId = reference("lesson_id", Lessons, onDelete = ReferenceOption.CASCADE)
    val question = text("question")
    val code = text("code").nullable()
    val options = jsonb<List<String>>("options", Json.Default).default(emptyList())
    val correctAnswer = integer("correct_answer").nullable()
    val order = integer("order").nullable()
}