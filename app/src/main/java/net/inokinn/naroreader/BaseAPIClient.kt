package net.inokinn.naroreader

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result

interface APIClientCallback {
    fun doSucceed(result: List<Any>)
    fun doFailed()
}

/**
 * HTTPクライアントの基底クラスです
 */
open class BaseAPIClient(params: Map<String, String>?, callback: APIClientCallback) {
    open var targetUrl = ""
    private val params = params
    open val callback = callback
    /**
     * リクエストを開始します
     */

    fun startRequest() {

        this.targetUrl += "?"

        if (params != null) {
            params.forEach {
                this.targetUrl = this.targetUrl + "&" + it.key + "=" + it.value
            }
        }

        this.targetUrl.httpGet().responseString { request, response, result ->
            when (result) {
                is Result.Success -> {
                    this.requestSucceed(response, result)
                }
                is Result.Failure -> {
                    this.requestFailed(response, result)
                }
            }
        }
    }

    /**
     * リクエスト成功時に呼ばれる
     */
    open fun requestSucceed(response: Response, result: Result<String, FuelError>) {}

    /**
     * リクエスト失敗時に呼ばれる
     */
    open fun requestFailed(response: Response, result: Result<String, FuelError>) {}
}