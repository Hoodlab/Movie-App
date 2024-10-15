package com.example.movieapp.ui.actor

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.actor_detail.domain.models.Actor
import com.example.movieapp.actor_detail.domain.repository.ActorRepository
import com.example.movieapp.utils.K
import com.example.movieapp.utils.collectAndHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActorViewModel @Inject constructor(
    private val actorRepository: ActorRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _actorState = MutableStateFlow(ActorState())
    val actorState = _actorState.asStateFlow()

    val id: Int = savedStateHandle.get<Int>(K.ACTOR_ID) ?: -1

    init {
        fetchActor()
    }

    private fun fetchActor() = viewModelScope.launch {
        if (id == -1) {
            _actorState.update {
                it.copy(isLoading = false, error = "Actor not found")
            }
        } else {
            actorRepository.fetchActorDetail(id).collectAndHandle(
                onError = { error ->
                    _actorState.update {
                        it.copy(isLoading = false, error = error?.message)
                    }
                },
                onLoading = {
                    _actorState.update {
                        it.copy(isLoading = true, error = null)
                    }
                }
            ) { movieDetail ->
                _actorState.update {
                    it.copy(isLoading = false, error = null, actor = movieDetail)
                }
            }
        }
    }


}

data class ActorState(
    val isLoading: Boolean = false,
    val actor: Actor? = null,
    val error: String? = null
)