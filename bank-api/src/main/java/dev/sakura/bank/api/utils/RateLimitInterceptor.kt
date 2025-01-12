package dev.sakura.bank.api.utils

import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

class RateLimitInterceptor : Interceptor {
    private var lastRequestTime: Long = 0L
    private val requestInterval = TimeUnit.SECONDS.toMillis(1)

    override fun intercept(chain: Interceptor.Chain): Response {
        synchronized(this) {
            val now = System.currentTimeMillis()
            val waitTime = requestInterval - (now - lastRequestTime)

            if (waitTime > 0) {
                Thread.sleep(waitTime)
            }
            lastRequestTime = System.currentTimeMillis()
        }
        return chain.proceed(chain.request())
    }
}
