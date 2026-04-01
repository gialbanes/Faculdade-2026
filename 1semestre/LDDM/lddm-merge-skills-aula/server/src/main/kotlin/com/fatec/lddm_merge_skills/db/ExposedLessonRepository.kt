package com.fatec.lddm_merge_skills.db

import com.fatec.lddm_merge_skills.db.dto.*
import com.fatec.lddm_merge_skills.model.Lesson
import com.fatec.lddm_merge_skills.repository.LessonRepository
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class ExposedLessonRepository : LessonRepository {

    override suspend fun getByCourseId(courseId: Int): List<Lesson> = newSuspendedTransaction {
        Lessons.selectAll()
            .where { Lessons.courseId eq courseId }
            .orderBy(Lessons.order)
            .map { it.toLesson() }
    }

    override suspend fun getById(id: Int): Lesson? = newSuspendedTransaction {
        Lessons.selectAll()
            .where { Lessons.id eq id }
            .map { it.toLesson() }
            .singleOrNull()
    }

    override suspend fun create(lesson: Lesson): Lesson = newSuspendedTransaction {
        val insertStatement = Lessons.insert { lesson.toInsertDTO().applyTo(it) }
        insertStatement.resultedValues!!.first().toLesson()
    }

    override suspend fun update(id: Int, lesson: Lesson): Lesson = newSuspendedTransaction {
        Lessons.update({ Lessons.id eq id }) { lesson.toInsertDTO().applyTo(it) }
        getById(id) ?: lesson
    }

    override suspend fun delete(id: Int) {
        newSuspendedTransaction {
            Lessons.deleteWhere { Lessons.id eq id }
        }
    }
}