package com.marcosnathan.ownvault.data.datasource.local.database.converters

import androidx.room.TypeConverter
import kotlin.time.Instant

class InstantConverter {
    @TypeConverter
    fun longToInstant(value: Long?): Instant? = value?.let(Instant::fromEpochMilliseconds)

    @TypeConverter
    fun instantToLong(value: Instant?): Long? = value?.toEpochMilliseconds()
}