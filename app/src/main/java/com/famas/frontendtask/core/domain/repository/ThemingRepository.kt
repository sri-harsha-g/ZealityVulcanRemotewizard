package com.famas.frontendtask.core.domain.repository

import com.famas.frontendtask.core.data.Response
import com.famas.frontendtask.core.data.remote.responses.ThemeColorsResponse

interface ThemingRepository {

    suspend fun getColors() : Response<ThemeColorsResponse>
}