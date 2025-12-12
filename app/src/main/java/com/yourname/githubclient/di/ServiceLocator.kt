package com.yourname.githubclient.di

import android.content.Context
import com.yourname.githubclient.data.local.DataStoreManager
import com.yourname.githubclient.data.remote.api.GithubApi
import com.yourname.githubclient.data.remote.api.NetworkInterceptor
import com.yourname.githubclient.data.repository.AuthRepositoryImpl
import com.yourname.githubclient.data.repository.ProfileRepositoryImpl
import com.yourname.githubclient.data.repository.ThemeRepositoryImpl
import com.yourname.githubclient.domain.repository.AuthRepository
import com.yourname.githubclient.domain.repository.ProfileRepository
import com.yourname.githubclient.domain.repository.ThemeRepository
import com.yourname.githubclient.domain.usecase.login.LoginUseCase
import com.yourname.githubclient.domain.usecase.login.LogoutUseCase
import com.yourname.githubclient.domain.usecase.profile.ClearAvatarUseCase
import com.yourname.githubclient.domain.usecase.profile.GetAvatarUseCase
import com.yourname.githubclient.domain.usecase.profile.GetUsernameUseCase
import com.yourname.githubclient.domain.usecase.profile.SaveAvatarUseCase
import com.yourname.githubclient.domain.usecase.theme.GetThemeUseCase
import com.yourname.githubclient.domain.usecase.theme.UpdateThemeUseCase
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceLocator {

    private val lock = Any()

    private var _dataStore: DataStoreManager? = null
    val dataStore get() = _dataStore!!

    private var _interceptor: NetworkInterceptor? = null
    val interceptor get() = _interceptor!!

    private var _api: GithubApi? = null
    val api get() = _api!!

    private var _authRepo: AuthRepository? = null
    val authRepository get() = _authRepo!!

    private var _profileRepo: ProfileRepository? = null
    val profileRepository get() = _profileRepo!!

    private var _themeRepo: ThemeRepository? = null
    val themeRepository get() = _themeRepo!!

    // Use cases
    private var _loginUseCase: LoginUseCase? = null
    val loginUseCase get() = _loginUseCase!!

    private var _logoutUseCase: LogoutUseCase? = null
    val logoutUseCase get() = _logoutUseCase!!

    private var _getAvatarUseCase: GetAvatarUseCase? = null
    val getAvatarUseCase get() = _getAvatarUseCase!!

    private var _getUsernameUseCase: GetUsernameUseCase? = null
    val getUsernameUseCase get() = _getUsernameUseCase!!

    private var _saveAvatarUseCase: SaveAvatarUseCase? = null
    val saveAvatarUseCase get() = _saveAvatarUseCase!!

    private var _clearAvatarUseCase: ClearAvatarUseCase? = null
    val clearAvatarUseCase get() = _clearAvatarUseCase!!

    private var _getThemeUseCase: GetThemeUseCase? = null
    val getThemeUseCase get() = _getThemeUseCase!!

    private var _updateThemeUseCase: UpdateThemeUseCase? = null
    val updateThemeUseCase get() = _updateThemeUseCase!!


    fun init(context: Context) {
        synchronized(lock) {

            _dataStore = DataStoreManager(context)
            _interceptor = NetworkInterceptor()

            val client = OkHttpClient.Builder()
                .addInterceptor(_interceptor!!)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            _api = retrofit.create(GithubApi::class.java)

            _authRepo = AuthRepositoryImpl(_api!!, _dataStore!!, _interceptor!!)
            _profileRepo = ProfileRepositoryImpl(_dataStore!!)
            _themeRepo = ThemeRepositoryImpl(_dataStore!!)

            _loginUseCase = LoginUseCase(_authRepo!!)
            _logoutUseCase = LogoutUseCase(_dataStore!!)

            _getAvatarUseCase = GetAvatarUseCase(_dataStore!!)
            _getUsernameUseCase = GetUsernameUseCase(_dataStore!!)
            _saveAvatarUseCase = SaveAvatarUseCase(_dataStore!!)
            _clearAvatarUseCase = ClearAvatarUseCase(_dataStore!!)

            _getThemeUseCase = GetThemeUseCase(_themeRepo!!)
            _updateThemeUseCase = UpdateThemeUseCase(_themeRepo!!)
        }
    }
}
