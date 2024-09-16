package com.flexnet.data.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.flexnet.data.room.dao.NetworkRuleDao
import com.flexnet.data.room.entity.NetworkRuleEntity

@Database(entities = [NetworkRuleEntity::class], version = 2)
internal abstract class FlexNetDatabase : RoomDatabase() {
    abstract fun networkRuleDao(): NetworkRuleDao
}
