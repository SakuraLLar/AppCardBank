package dev.sakura.bank.database

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.sakura.bank.database.dao.BinHistoryDAO
import dev.sakura.bank.database.entity.BinHistoryEntity

@Database(entities = [BinHistoryEntity::class], version = 2, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun binHistoryDAO(): BinHistoryDAO
}
