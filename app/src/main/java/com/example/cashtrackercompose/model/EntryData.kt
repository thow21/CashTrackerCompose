package com.example.cashtrackercompose.model

import com.example.cashtrackercompose.entry.Entry

interface EntryData {
    suspend fun getEntries() : List<Entry>

    suspend fun addEntry(
        entry: Entry
    ) : Boolean

    suspend fun deleteEntry(
        entry: Entry
    ) : Boolean
}