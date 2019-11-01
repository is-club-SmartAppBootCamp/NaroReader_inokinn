package net.inokinn.naroreader

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

/**
 * なろうのランキングを取得するクラスです
 */
class NaroRankingAPIClient(params: Map<String, String>?, callback: APIClientCallback) : BaseAPIClient(params, callback) {
    override var targetUrl = " https://api.syosetu.com/novelapi/api/"

    /**
     * リクエスト成功時に呼ばれる
     */
    override fun requestSucceed(response: Response, result: Result<String, FuelError>) {
        val type = Types.newParameterizedType(List::class.java, Novel::class.java)
        val adapter: JsonAdapter<List<Novel>> = Moshi.Builder().build().adapter(type)
        val novels = (adapter.fromJson(result.get()) as List<Any>).filterIndexed{ i, _ -> i != 0}

        this.callback.doSucceed(novels)
    }

    /**
     * リクエスト失敗時に呼ばれる
     */
    override fun requestFailed(response: Response, result: Result<String, FuelError>) {
        //
    }
}