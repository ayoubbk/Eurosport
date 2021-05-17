package com.bks.eurosporttest.data.interactors.featured

import com.bks.eurosporttest.data.network.ApiService
import com.bks.eurosporttest.data.network.MockWebServerResponses.videosAndStoriesResponse
import com.bks.eurosporttest.data.network.mapper.StoryDtoMapper
import com.bks.eurosporttest.data.network.mapper.VideoDtoMapper
import com.bks.eurosporttest.domain.model.Story
import com.bks.eurosporttest.domain.model.Video
import com.bks.eurosporttest.interactors.featured.GetFeaturedItemUsecase
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import okhttp3.HttpUrl
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class GetVideosAndStoriesTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var baseUrl: HttpUrl

    // system in test
    private lateinit var getVideosAndStories: GetFeaturedItemUsecase

    // dependencies
    private lateinit var apiService: ApiService
    private val videoDtoMapper = VideoDtoMapper()
    private val storyDtoMapper = StoryDtoMapper()

    @BeforeEach
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        baseUrl = mockWebServer.url("/api/json-storage/bin/")
        apiService = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder()
                .create()))
            .build()
            .create(ApiService::class.java)

        getVideosAndStories = GetFeaturedItemUsecase(
            apiService,
            videoDtoMapper,
            storyDtoMapper
        )
    }


    /**
     * 1. Are videos and stories retrieved from the network ?
     * 2. Are videos and stories emitted as a flow ?
     */
    @Test
    fun getVideosAndStoriesFromNetwork_emitVideosAndStories() = runBlocking {
        // condition the response
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(videosAndStoriesResponse)
        )

        // execute the use case
        val flowItems = getVideosAndStories.execute().toList()

        // first emission should be Loading status
        assert(flowItems[0].loading)

        // second emission should be the list of videosAndStories
        val videosAndStories = flowItems[1].data
        assert(videosAndStories?.size?: 0 > 0)

        // confirm they are either Videos or Story objects
        assert(videosAndStories?.get(0) is Video
                ||videosAndStories?.get(0) is Story)

        // ensure Loading is false now
        assert(!flowItems[1].loading)
    }

    @Test
    fun getVideosAndStoriesFromNetwork_emitMixedList() = runBlocking {
        // condition the response
        mockWebServer.enqueue(
                MockResponse()
                        .setResponseCode(HttpURLConnection.HTTP_OK)
                        .setBody(videosAndStoriesResponse)
        )

        // execute the use case
        val flowItems = getVideosAndStories.execute().toList()

        // first emission should be Loading status
        assert(flowItems[0].loading)

        // second emission should be the mixed list of videosAndStories
        val videosAndStories = flowItems[1].data
        assert(videosAndStories?.size?: 0 > 0)



    }

    @Test
    fun getVideosAndStoriesFromNetwork_emitHttpError() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .setBody("{}")
        )

        val flowItems = getVideosAndStories.execute().toList()

        assert(flowItems[0].loading)

        val error = flowItems[1].error
        assert(error != null)

        assert(!flowItems[1].loading)
    }

    @AfterEach
    fun tearDown() {
        mockWebServer.shutdown()
    }

}