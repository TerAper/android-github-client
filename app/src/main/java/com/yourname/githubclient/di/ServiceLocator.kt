package com.yourname.githubclient.di

import android.content.Context
import androidx.room.Room
import com.yourname.githubclient.data.local.AppDatabase
import com.yourname.githubclient.data.local.DataStoreManager
import com.yourname.githubclient.data.remote.api.GithubApi
import com.yourname.githubclient.data.remote.api.NetworkInterceptor
import com.yourname.githubclient.data.repository.*
import com.yourname.githubclient.domain.repository.*
import com.yourname.githubclient.domain.usecase.details.GetUserDetailsUseCase
import com.yourname.githubclient.domain.usecase.details.GetUserReposUseCaseForDetails
import com.yourname.githubclient.domain.usecase.login.LoginUseCase
import com.yourname.githubclient.domain.usecase.login.LogoutUseCase
import com.yourname.githubclient.domain.usecase.profile.*
import com.yourname.githubclient.domain.usecase.repositories.GetUserRepositoriesUseCase
import com.yourname.githubclient.domain.usecase.theme.GetThemeUseCase
import com.yourname.githubclient.domain.usecase.theme.UpdateThemeUseCase
import com.yourname.githubclient.domain.usecase.users.GetAllUsersUseCase
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ServiceLocator {

    private val lock = Any()

    // Managers / primitives
    private var _dataStore: DataStoreManager? = null
    val dataStore get() = _dataStore!!

    private var _interceptor: NetworkInterceptor? = null
    val interceptor get() = _interceptor!!

    // Retrofit API
    private var _api: GithubApi? = null
    val api get() = _api!!

    // Room DB
    private var _appDatabase: AppDatabase? = null
    val appDatabase get() = _appDatabase!!

    // Repositories
    private var _authRepo: AuthRepository? = null
    val authRepository get() = _authRepo!!

    private var _profileRepo: ProfileRepository? = null
    val profileRepository get() = _profileRepo!!

    private var _themeRepo: ThemeRepository? = null
    val themeRepository get() = _themeRepo!!

    private var _repositoriesRepo: RepositoriesRepository? = null
    val repositoriesRepository get() = _repositoriesRepo!!

    private var _detailsRepository: DetailsRepository? = null
    val detailsRepository get() = _detailsRepository!!

    private var _usersRepo: UsersRepository? = null
    val usersRepository get() = _usersRepo!!

    // Use Cases
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

    private var _getAllUsersUseCase: GetAllUsersUseCase? = null
    val getAllUsersUseCase get() = _getAllUsersUseCase!!

    private var _getUserDetailsUseCase: GetUserDetailsUseCase? = null
    val getUserDetailsUseCase get() = _getUserDetailsUseCase!!

    private var _getUserReposUseCaseForDetails: GetUserReposUseCaseForDetails? = null
    val getUserReposUseCaseForDetails get() = _getUserReposUseCaseForDetails!!

    fun init(context: Context) {
        synchronized(lock) {
            // Initialize DataStore
            _dataStore = DataStoreManager(context)

            // Network interceptor
            _interceptor = NetworkInterceptor()

            // Retrofit API
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

            // Room database
            _appDatabase = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "github_client_db"
            ).build()

            // DAOs
            val userDao = _appDatabase!!.userDao()
            val repoDao = _appDatabase!!.repositoryDao()

            // Repositories
            _authRepo = AuthRepositoryImpl(_api!!, _dataStore!!, _interceptor!!)
            _profileRepo = ProfileRepositoryImpl(_dataStore!!)
            _themeRepo = ThemeRepositoryImpl(_dataStore!!)
            _repositoriesRepo = RepositoriesRepositoryImpl(_api!!, repoDao, context)
            _detailsRepository = DetailsRepositoryImpl(_api!!, userDao, repoDao, context)
            _usersRepo = UsersRepositoryImpl(_api!!, userDao)

            // Use cases
            _loginUseCase = LoginUseCase(_authRepo!!)
            _logoutUseCase = LogoutUseCase(_dataStore!!)

            _getAvatarUseCase = GetAvatarUseCase(_dataStore!!)
            _getUsernameUseCase = GetUsernameUseCase(_dataStore!!)
            _saveAvatarUseCase = SaveAvatarUseCase(_dataStore!!)
            _clearAvatarUseCase = ClearAvatarUseCase(_dataStore!!)

            _getThemeUseCase = GetThemeUseCase(_themeRepo!!)
            _updateThemeUseCase = UpdateThemeUseCase(_themeRepo!!)

            _getUserDetailsUseCase = GetUserDetailsUseCase(_detailsRepository!!)
            _getUserReposUseCaseForDetails = GetUserReposUseCaseForDetails(_detailsRepository!!)

            _getUserRepositoriesUseCase = GetUserRepositoriesUseCase(_repositoriesRepo!!)
            _getAllUsersUseCase = GetAllUsersUseCase(_usersRepo!!)
        }
    }
}
