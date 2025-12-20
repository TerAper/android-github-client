package com.aper.feature_profile_repos.domain.usecase

import com.aper.core.sessionData.AppSessionData
import com.aper.core.sessionData.SessionDataKey
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.rx3.asObservable
import javax.inject.Inject

class ObserveUsernameRxUseCase @Inject constructor(
    sessionData: AppSessionData
) {
    val username: Observable<String> =
        sessionData.observe(SessionDataKey.LoginKey)
            .filterNotNull()
            .asObservable()
}
