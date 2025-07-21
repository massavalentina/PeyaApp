package com.example.peya_ecommerce_app

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description


@ExperimentalCoroutinesApi
class MainDispatcher(val testDispatcher : TestDispatcher = StandardTestDispatcher()
) : TestWatcher() {

    override fun starting(description: Description?){
        Dispatchers.setMain(dispatcher = testDispatcher)
    }

    override fun finished(description: Description?) {
        Dispatchers.resetMain()
    }

}