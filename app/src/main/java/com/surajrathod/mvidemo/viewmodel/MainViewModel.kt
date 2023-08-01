package com.surajrathod.mvidemo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.surajrathod.mvidemo.intent.MainIntent
import com.surajrathod.mvidemo.model.Users
import com.surajrathod.mvidemo.repository.MainRepoImpl
import com.surajrathod.mvidemo.repository.MainRepository
import com.surajrathod.mvidemo.viewstate.MainState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(private val repo: MainRepository) : ViewModel() {

    val userIntent = Channel<MainIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<MainState>(MainState.Idle)
    val state: StateFlow<MainState> get() = _state

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                when (it) {
                    is MainIntent.fetchUser -> fetchUser()
                    is MainIntent.showMessage -> {
                        displayMessage(it.msg)
                    }
                }
            }
        }
    }

    private fun displayMessage(msg: String?) {
        viewModelScope.launch {
            _state.update {
                MainState.ShowMsg(msg)
            }
        }
    }

    private fun fetchUser() {
        viewModelScope.launch {
            _state.value = MainState.Loading
            _state.value = try {
                MainState.Users(repo.loadUsers())
            } catch (e: Exception) {
                MainState.Error(e.localizedMessage)
            }
        }
    }


}