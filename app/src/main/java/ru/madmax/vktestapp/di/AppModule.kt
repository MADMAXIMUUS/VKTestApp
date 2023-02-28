package ru.madmax.vktestapp.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import ru.madmax.vktestapp.data.dataSource.GifApi
import ru.madmax.vktestapp.data.repository.GifRepositoryImpl
import ru.madmax.vktestapp.domain.repository.GifRepository
import ru.madmax.vktestapp.domain.useCase.GetByIdUseCase
import ru.madmax.vktestapp.domain.useCase.GetTrendingUseCase
import ru.madmax.vktestapp.domain.useCase.GifUseCases
import ru.madmax.vktestapp.domain.useCase.SearchUseCase
import ru.madmax.vktestapp.util.Constants.BASE_URL
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun provideGifApi(client: OkHttpClient): GifApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(client)
            .build()
            .create(GifApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGifRepository(gifApi: GifApi): GifRepository {
        return GifRepositoryImpl(gifApi)
    }

    @Provides
    @Singleton
    fun provideGifUseCases(gifRepository: GifRepository): GifUseCases {
        return GifUseCases(
            getTrendingUseCase = GetTrendingUseCase(gifRepository),
            searchUseCase = SearchUseCase(gifRepository),
            getByIdUseCase = GetByIdUseCase(gifRepository)
        )
    }
}