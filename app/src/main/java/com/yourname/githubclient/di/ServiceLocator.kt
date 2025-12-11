package com.yourname.githubclient.di

import android.content.Context
import com.yourname.githubclient.data.local.DataStoreManager
import com.yourname.githubclient.data.remote.api.GithubApi
import com.yourname.githubclient.data.remote.api.NetworkInterceptor
import com.yourname.githubclient.data.repository.AuthRepositoryImpl
import com.yourname.githubclient.data.repository.ThemeRepositoryImpl
import com.yourname.githubclient.domain.repository.AuthRepository
import com.yourname.githubclient.domain.repository.ThemeRepository
import com.yourname.githubclient.domain.usecase.GetThemeUseCase
import com.yourname.githubclient.domain.usecase.LoginUseCase
import com.yourname.githubclient.domain.usecase.LogoutUseCase
import com.yourname.githubclient.domain.usecase.UpdateThemeUseCase
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

    private var _themeRepo: ThemeRepository? = null
    val themeRepository get() = _themeRepo!!

    private var _loginUseCase: LoginUseCase? = null
    val loginUseCase get() = _loginUseCase!!

    private var _logoutUseCase: LogoutUseCase? = null
    val logoutUseCase get() = _logoutUseCase!!

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
            _themeRepo = ThemeRepositoryImpl(_dataStore!!)

            _loginUseCase = LoginUseCase(_authRepo!!)
            _logoutUseCase = LogoutUseCase(_dataStore!!)
            _getThemeUseCase = GetThemeUseCase(_themeRepo!!)
            _updateThemeUseCase = UpdateThemeUseCase(_themeRepo!!)
        }
    }
}
