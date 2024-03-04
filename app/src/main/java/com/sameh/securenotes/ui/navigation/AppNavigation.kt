package com.sameh.securenotes.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sameh.securenotes.feature_note.presentation.add_edit_note.AddEditNoteScreen
import com.sameh.securenotes.feature_note.presentation.notes_list.NotesScreen
import com.sameh.securenotes.ui.navigation.Routes.AddEditNoteScreen.Companion.colorArgKey
import com.sameh.securenotes.ui.navigation.Routes.AddEditNoteScreen.Companion.idArgKey

@Composable
fun AppNavigationGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.NotesScreen.route
    ) {
        composable(Routes.NotesScreen.route) {
            NotesScreen {
                navController.navigate(Routes.AddEditNoteScreen(it).route)
            }
        }
        composable(
            Routes.AddEditNoteScreen(null).route,
            arguments = listOf(navArgument(name = idArgKey) {
                type = NavType.IntType
                defaultValue = -1
            },
                navArgument(name = colorArgKey) {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) { AddEditNoteScreen() }
    }
}