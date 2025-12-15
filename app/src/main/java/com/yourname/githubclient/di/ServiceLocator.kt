package com.yourname.githubclient.di

import android.content.Context
import androidx.room.Room
import com.yourname.githubclient.data.local.AppDatabase
import com.yourname.githubclient.data.local.DataStoreManager
import com.yourname.githubclient.data.remote.api.GithubApi
import com.yourname.githubclient.data.remote.api.NetworkInterceptor
import com.yourname.githubclient.data.repository.*
import com.yourname.githubclient.domain.repository.*
import com.yourname.githubclient.domain.usecase.details.GetUserReposUseCaseForDetails
import com.yourname.githubclient.domain.usecase.login.LoginUseCase
import com.yourname.githubclient.domain.usecase.login.LogoutUseCase
import com.yourname.githubclient.domain.usecase.profile.*
import com.yourname.githubclient.domain.usecase.repositories.GetUserRepositoriesUseCase
import com.yourname.githubclient.domain.usecase.theme.GetThemeUseCase
import com.yourname.githubclient.domain.usecase.theme.UpdateThemeUseCase
import com.yourname.githubclient.domain.usecase.users.GetUsersUseCase
import com.yourname.githubclient.domain.usecase.users.StartUsersSessionUseCase
import com.yourname.githubclient.util.AndroidNetworkChecker
import com.yourname.githubclient.util.NetworkChecker
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ServiceLocator {
    private val lock = Any()

    private var _dataStore: DataStoreManager? = null
    val dataStore get() = _dataStore!!

    private var _interceptor: NetworkInterceptor? = null
    val interceptor get() = _interceptor!!

    private var _api: GithubApi? = null
    val api get() = _api!!

    private var _appDatabase: AppDatabase? = null
    val appDatabase get() = _appDatabase!!

    private var _authRepo: AuthRepository? = null
    val authRepository get() = _authRepo!!

    private var _profileRepo: ProfileRepository? = null
    val profileRepository get() = _profileRepo!!

    private var _themeRepo: ThemeRepository? = null
    val themeRepository get() = _themeRepo!!

    private var _repositoriesRepo: ProfileRepoRepository? = null
    val repositoriesRepository get() = _repositoriesRepo!!

    private var _detailsRepository: DetailsRepository? = null
    val detailsRepository get() = _detailsRepository!!

    private var _networkChecker: NetworkChecker? = null
    val networkChecker get() = _networkChecker!!

    private var _usersRepository: UsersRepository? = null
    val usersRepository get() = _usersRepository!!

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

    private var _getUserRepositoriesUseCase: GetUserRepositoriesUseCase? = null
    val getUserRepositoriesUseCase get() = _getUserRepositoriesUseCase!!

    private var _getUsersUseCase: GetUsersUseCase? = null
    val getUsersUseCase get() = _getUsersUseCase!!

    private var _getUserReposUseCaseForDetails: GetUserReposUseCaseForDetails? = null
    val getUserReposUseCaseForDetails get() = _getUserReposUseCaseForDetails!!

    private var _startUsersSessionUseCase: StartUsersSessionUseCase? = null
    val startUsersSessionUseCase get() = _startUsersSessionUseCase!!


    fun init(context: Context) {
        synchronized(lock) {
            _dataStore = DataStoreManager(context)
            _interceptor = NetworkInterceptor()
            _networkChecker = AndroidNetworkChecker(context)

            val client = OkHttpClient.Builder()
                .addInterceptor(_interceptor!!)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()

            _api = retrofit.create(GithubApi::class.java)

            _appDatabase = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "github_client_db"
            ).build()

            val userDao = _appDatabase!!.userDao()
            val repoDao = _appDatabase!!.repositoryDao()

            _authRepo = AuthRepositoryImpl(_api!!, _dataStore!!, _interceptor!!)
            _profileRepo = ProfileRepositoryImpl(_dataStore!!)
            _themeRepo = ThemeRepositoryImpl(_dataStore!!)
            _repositoriesRepo = ProfileRepoRepositoryImpl(_api!!, repoDao, _networkChecker!!)
            _detailsRepository = DetailsRepositoryImpl(_api!!, repoDao, _networkChecker!!)
            _usersRepository = UsersRepositoryImpl(_api!!, userDao,repoDao, _networkChecker!!,
                dataStore)

            _loginUseCase = LoginUseCase(_authRepo!!)
            _logoutUseCase = LogoutUseCase(_dataStore!!,_repositoriesRepo!!,_usersRepository!!)

            _getAvatarUseCase = GetAvatarUseCase(_dataStore!!)
            _getUsernameUseCase = GetUsernameUseCase(_dataStore!!)
            _saveAvatarUseCase = SaveAvatarUseCase(_dataStore!!)
            _clearAvatarUseCase = ClearAvatarUseCase(_dataStore!!)

            _getThemeUseCase = GetThemeUseCase(_themeRepo!!)
            _updateThemeUseCase = UpdateThemeUseCase(_themeRepo!!)

            _getUserReposUseCaseForDetails = GetUserReposUseCaseForDetails(_detailsRepository!!)

            _getUserRepositoriesUseCase = GetUserRepositoriesUseCase(_repositoriesRepo!!)
            _getUsersUseCase = GetUsersUseCase(_usersRepository!!)
            _startUsersSessionUseCase = StartUsersSessionUseCase(_usersRepository!!)
            _startUsersSessionUseCase
        }
    }
}
