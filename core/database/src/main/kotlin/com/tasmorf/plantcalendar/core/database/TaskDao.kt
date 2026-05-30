package com.tasmorf.plantcalendar.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks WHERE scheduledDate BETWEEN :startDate AND :endDate")
    fun getTasksInRange(startDate: LocalDate, endDate: LocalDate): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE id = :id")
    suspend fun getTaskById(id: String): TaskEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTasks(tasks: List<TaskEntity>)

    @Update
    suspend fun updateTask(task: TaskEntity)

    /**
     * Finds all incomplete tasks of a specific type for a specific plant that are scheduled
     * on or before a given date.
     */
    @Query("""
        UPDATE tasks 
        SET completedAt = :completedAt 
        WHERE plantId = :plantId 
        AND type = :type 
        AND scheduledDate <= :onOrBeforeDate 
        AND completedAt IS NULL
    """)
    suspend fun markPreviousTasksAsCompleted(
        plantId: String,
        type: String,
        onOrBeforeDate: LocalDate,
        completedAt: LocalDateTime
    )

    /**
     * Gets pending tasks (past incomplete tasks).
     * Returns the latest occurrence for each plant and task type.
     */
    @Query("""
        SELECT * FROM tasks 
        WHERE scheduledDate < :today 
        AND completedAt IS NULL 
        GROUP BY plantId, type 
        HAVING scheduledDate = MAX(scheduledDate)
    """)
    fun getPendingTasks(today: LocalDate): Flow<List<TaskEntity>>
}
