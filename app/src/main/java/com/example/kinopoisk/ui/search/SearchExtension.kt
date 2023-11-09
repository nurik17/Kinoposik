package com.example.kinopoisk.ui.search

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

@OptIn(FlowPreview::class)
@ExperimentalCoroutinesApi
fun EditText.textInputAsFlow(): Flow<String> {
    val textStateFlow = MutableStateFlow(text.toString())
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun afterTextChanged(p0: Editable?) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            textStateFlow.value = p0.toString()
        }
    })
    return textStateFlow
        .debounce(100)
        .map { it.trim() }
        .distinctUntilChanged()
        .filter { it.isNotBlank() }
}
