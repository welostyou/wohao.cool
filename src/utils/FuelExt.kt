package utils

import com.github.kittinunf.fuel.httpGet

val String.responseString: String?
    get() = this.httpGet().responseString().third.component1()