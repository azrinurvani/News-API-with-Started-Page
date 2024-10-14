package com.azrinurvani.newsappwithstartedpage.domain.usecases.app_entry

import com.azrinurvani.newsappwithstartedpage.domain.manager.LocalUserManager

class SaveAppEntry(
    private val localUserManager : LocalUserManager
) {

    suspend operator fun invoke(){
        localUserManager.saveAppEntry()
    }
}