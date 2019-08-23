package com.rosberry.sample.workmanager.di

import android.content.Context
import androidx.room.Room
import com.rosberry.sample.workmanager.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author mmikhailov on 14/01/2019.
 */
@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun providesAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "app-db")
            .allowMainThreadQueries()
            .build()
    }
}