package com.flexnet.data.di

import android.content.Context
import androidx.room.Room
import com.flexnet.data.room.dao.NetworkRuleDao
import com.flexnet.data.room.database.FlexNetDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal object Provide {
    @Singleton
    @Provides
    fun provideFlexNetDatabase(appContext: Context): FlexNetDatabase {
        return Room.databaseBuilder(appContext, FlexNetDatabase::class.java, "network_rules")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideNetworkRuleDao(database: FlexNetDatabase): NetworkRuleDao {
        return database.networkRuleDao()
    }
}
