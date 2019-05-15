package utils

import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.httpGet

val String.responseString: String?
    get() = this.httpGet().responseString().third.component1()

val Request.responseString: String?
    get() = responseString().third.component1()