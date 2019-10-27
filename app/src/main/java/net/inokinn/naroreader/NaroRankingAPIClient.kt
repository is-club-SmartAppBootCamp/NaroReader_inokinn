package net.inokinn.naroreader

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.json.FuelJson
import com.github.kittinunf.result.Result

/**
 * なろうのランキングを取得するクラスです
 */
class NaroRankingAPIClient(params: Map<String, String>?, callback: APIClientCallback) : BaseAPIClient(params, callback) {
    override var targetUrl = " https://api.syosetu.com/novelapi/api/"
    var novels = arrayListOf<Any>()

    /**
     * リクエスト成功時に呼ばれる
     */
    override fun requestSucceed(response: Response, result: Result<FuelJson, FuelError>) {
        val novelsJson = result.get().array()

        //JSONArrayはforEach使えないのでやむなくfor文を使う
        for (i in 1..(novelsJson.length() - 1)) {
            val item = novelsJson.getJSONObject(i)
            val novel = Novel(item.getString("title"), item.getString("ncode"), item.getString("writer"))
            this.novels.add(novel)
        }
        result.get().array()
        this.callback.doSucceed(this.novels)
    }

    /**
     * リクエスト失敗時に呼ばれる
     */
    override fun requestFailed(response: Response, result: Result<FuelJson, FuelError>) {
        //
    }
}