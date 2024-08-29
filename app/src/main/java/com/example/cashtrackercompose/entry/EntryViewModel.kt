package com.example.cashtrackercompose.entry

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import at.florianschuster.control.Controller
import at.florianschuster.control.createController
import com.example.cashtrackercompose.model.EntryData
import com.example.cashtrackercompose.ui.baseui.ControllerViewModel
import kotlinx.coroutines.flow.flow

class EntryViewModel(
    private val entryData: EntryData,
    private val context: Context,
) : ControllerViewModel<EntryViewModel.Action, EntryViewModel.State>() {

    open class Action {
        data class SaveEntry(val entry: Entry) : Action()
        data class UpdateTag1(val newPlace: String) : Action()
        data object NavigateToAdd : Action()
        data object RefreshData : Action()
        data class UpdateDetailEntry(val entry: Entry) : Action()
        data object DeleteEntry : Action()
        data class AddEntry(val entry: Entry) : Action()
    }

    open class Mutation {
        data class UpdatePlace(val place: String) : Mutation()
        data class RefreshData(val data: List<Entry>) : Mutation()
        data class ToggleLoading(val isLoading: Boolean) : Mutation()
        data class UpdateDetailEntry(val entry: Entry) : Mutation()
    }

    data class State(
        val place: String = "",
        val product: String = "",
        val notes: String = "",
        val date: String = "",
        val price: Float = 0f,
        val data: List<Entry> = listOf(),
        val isLoading: Boolean = false,
        val detailEntry: Entry? = null,
    )


    override val controller: Controller<Action, State> = viewModelScope.createController(
        initialState = State(),
        mutator = { action ->
            when (action) {
                is Action.SaveEntry -> flow {
                    if (
                        entryData.addEntry(action.entry)) {
                        Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Could not save", Toast.LENGTH_SHORT).show()
                    }
                    emit(Mutation.RefreshData(entryData.getEntries()))

                }

                is Action.UpdateTag1 -> flow {
                    emit(Mutation.UpdatePlace(action.newPlace))
                }

                is Action.NavigateToAdd -> flow {

                }

                is Action.RefreshData -> flow {
                    emit(Mutation.ToggleLoading(true))
                    emit(Mutation.RefreshData(entryData.getEntries()))
                }

                Action.DeleteEntry -> flow {
                    if (entryData.deleteEntry(state.value.detailEntry!!)) {
                        Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Could not delete", Toast.LENGTH_SHORT).show()
                    }
                    emit(Mutation.RefreshData(entryData.getEntries()))
                }

                is Action.AddEntry -> flow {
                    entryData.addEntry(action.entry)
                }

                is Action.UpdateDetailEntry -> flow {
                    emit(Mutation.UpdateDetailEntry(action.entry))
                }

                else -> flow {

                }


            }

        },
        reducer = { mutation, previousState ->
            when (mutation) {
                is Mutation.UpdatePlace -> previousState.copy(
                    place = mutation.place
                )

                is Mutation.RefreshData -> previousState.copy(
                    data = mutation.data,
                    isLoading = false
                )

                is Mutation.ToggleLoading -> previousState.copy(
                    isLoading = mutation.isLoading
                )

                is Mutation.UpdateDetailEntry -> previousState.copy(
                    detailEntry = mutation.entry
                )

                else -> previousState.copy(

                )
            }

        }
    )

}