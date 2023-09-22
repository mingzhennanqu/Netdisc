package com.lulu.favorite.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun AllFilesPage(
    folders: List<String>?,
) {
    var isShowHomePage by remember { mutableStateOf(true) }
    var isShowSecond by remember { mutableStateOf(false) }


    if (isShowHomePage) {
        AllFilesPageFirst(
            folders = folders,
            isShowHomePage = isShowHomePage,
            setIsShowHomePage = { isShowHomePage = it }
        )
    }
    if (isShowSecond) {
        AllFilesPageSecond()
    }

}
