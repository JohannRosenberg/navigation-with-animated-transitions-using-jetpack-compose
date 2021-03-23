package dev.wirespec.adoptme.ui.screens.main

import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarHostState
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    var scaffoldState: ScaffoldState = ScaffoldState(DrawerState(initialValue = DrawerValue.Closed), SnackbarHostState())
}