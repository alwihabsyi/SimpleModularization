package com.alwihbsyi.simplemodularization.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alwihbsyi.simplemodularization.core.data.model.Users
import com.alwihbsyi.simplemodularization.core.data.model.UsersDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val nameDao: UsersDao): ViewModel() {
    val users = nameDao.getAllUsername()
    private val _responseSave = Channel<Boolean>()
    val responseSave = _responseSave.receiveAsFlow()

    fun addUsers(users: Users) = viewModelScope.launch {
        nameDao.insert(users)
        _responseSave.send(true)
    }
}