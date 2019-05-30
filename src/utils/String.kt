package utils

import java.net.URLDecoder
import java.net.URLEncoder

//URL编码
val String.encodeURL: String get() = URLEncoder.encode(this, "UTF-8")

//URL解码
val String.decodeURL: String get() = URLDecoder.decode(this, "UTF-8")