package ru.animbus.ordomentis.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.koin.dsl.module
import ru.animbus.ordomentis.data.repositories.api.ItemApiRepository
import ru.animbus.ordomentis.data.repositories.api.UnitApiRepository
import ru.animbus.ordomentis.data.repositories.api.UserApiRepository
import ru.animbus.ordomentis.data.webapi.RetrofitFactory
import ru.animbus.ordomentis.data.webapi.clients.ItemApiClient
import ru.animbus.ordomentis.data.webapi.clients.UnitApiClient
import ru.animbus.ordomentis.data.webapi.clients.UserApiClient
import ru.animbus.ordomentis.data.webapi.interfaces.ItemApi
import ru.animbus.ordomentis.data.webapi.interfaces.UnitApi
import ru.animbus.ordomentis.data.webapi.interfaces.UserApi

val webApiModule = module {
    // Gson для сериализации/десериализации
    single<Gson> {
        GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .create()
    }

    // Retrofit фабрика
    single<RetrofitFactory> {
        val baseUrl = "https://ordomentis.pro/api1/"
        RetrofitFactory(baseUrl, get())
    }

    // API интерфейсы
    single<UserApi> {
        get<RetrofitFactory>().createUserApi()
    }

    single<UnitApi> {
        get<RetrofitFactory>().createUnitApi()
    }

    single<ItemApi> {
        get<RetrofitFactory>().createItemApi()
    }

    // API клиенты
    single<UserApiClient> {
        UserApiClient(get())
    }

    single<UnitApiClient> {
        UnitApiClient(get())
    }

    single<ItemApiClient> {
        ItemApiClient(get())
    }

    // API репозитории
    single<UserApiRepository> {
        UserApiRepository(get())
    }

    single<UnitApiRepository> {
        UnitApiRepository(get())
    }

    single<ItemApiRepository> {
        ItemApiRepository(get())
    }
}