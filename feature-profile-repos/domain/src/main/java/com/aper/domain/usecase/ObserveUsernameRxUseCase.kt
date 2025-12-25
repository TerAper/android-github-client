package com.aper.domain.usecase

import com.aper.core.session.AppSessionData
import com.aper.core.session.SessionDataKey
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
