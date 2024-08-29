package com.example.cashtrackercompose.ui.baseui

import androidx.lifecycle.ViewModel
import at.florianschuster.control.Controller
import kotlinx.coroutines.flow.StateFlow

abstract class ControllerViewModel<Action, State> : ViewModel() {

    protected abstract val controller: Controller<Action, State>

    fun dispatch(action: Action) = controller.dispatch(action)
    val currentState: State get() = controller.state.value
    val state: StateFlow<State> get() = controller.state
}