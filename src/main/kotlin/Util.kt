class Util {
    companion object {
        const val appName = "CORS Bypass"
        const val appVersion = "1.0.0"
        const val originPayload = "Origin: http://localhost"
        const val originIonicPayload = "Origin: ionic://localhost"
        const val origin = "Origin"
        /** Excluded ****
        "Cf-Connecting-Ip", "X-Original-Url", "X-Rewrite-Url", "Client-IP",
         **/
        val headers = arrayListOf("Base-Url", "Http-Url", "Proxy-Host", "Proxy-Url", "Real-Ip", "Redirect",
            "Referer", "Referrer", "Referer", "Request-Uri", "Uri", "Url", "X-Client-Ip", "X-Forwarded-For",
            "X-Client-IP", "X-Custom-IP-Authorization", "X-Forward-For", "X-Forwarded-By",
            "X-Forwarded-By-Original", "X-Forwarded-For-Original", "X-Forwarded-For", "X-Forwarded-Host",
            "X-Forwarded-Server", "X-Forwarder-For", "X-HTTP-Destinationurl", "X-Http-Host-Override",
            "X-Original-Remote-Addr", "X-Originating-IP", "X-Proxy-Url", "X-Remote-Addr",
            "X-Remote-IP", "X-True-IP", "Fastly-Client-Ip", "True-Client-Ip", "X-Cluster-Client-Ip",
            "X-Forwarded", "Forwarded-For", "Forwarded", "X-Real-Ip"
            // "Cf-Connecting-Ip", "X-Original-Url", "X-Rewrite-Url", "Client-IP"
        )
        const val ipAddress = "127.0.0.1"
    }
}