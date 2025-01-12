package dev.sakura.bank.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bin_history")
data class BinHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "bin") val bin: String,
    @ColumnInfo(name = "scheme") val scheme: String?,
    @ColumnInfo(name = "type") val type: String?,
    @ColumnInfo(name = "brand") val brand: String?,
    @ColumnInfo(name = "country") val country: String?,
    @ColumnInfo(name = "bank") val bank: String?,
    @ColumnInfo(name = "timestamp") val timestamp: Long = System.currentTimeMillis()
)
