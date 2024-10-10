package com.azrinurvani.newsappwithstartedpage.domain.usecases

import com.azrinurvani.newsappwithstartedpage.domain.manager.LocalUserManager
import kotlinx.coroutines.flow.Flow

class ReadAppEntry(
    private val localUserManager : LocalUserManager
) {

    suspend operator fun invoke(): Flow<Boolean> {
        return localUserManager.readAppEntry()
    }
}