package com.aper.domain.usecase

import com.aper.core_domain.session.AppSessionData
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.rx3.asObservable
import javax.inject.Inject

class ObserveUsernameRxUseCase @Inject constructor(
    sessionData: AppSessionData
) {
    val username: Observable<String> =
        sessionData.observeLogin()
            .filterNotNull()
            .asObservable()
}
