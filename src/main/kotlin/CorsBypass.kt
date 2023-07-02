
import burp.api.montoya.BurpExtension
import burp.api.montoya.MontoyaApi
import burp.api.montoya.http.message.requests.HttpRequest
import burp.api.montoya.logging.Logging
import burp.api.montoya.proxy.http.InterceptedRequest
import burp.api.montoya.proxy.http.ProxyRequestHandler
import burp.api.montoya.proxy.http.ProxyRequestReceivedAction
import burp.api.montoya.proxy.http.ProxyRequestToBeSentAction

class CorsBypass: BurpExtension, ProxyRequestHandler {
    private var logger: Logging? = null

    override fun initialize(api: MontoyaApi?) {
        // set extension name
        api!!.extension().setName("${Util.appName} - ${Util.appVersion}")

        // logging
        logger = api.logging()
        logger!!.logToOutput("${Util.appName} - ${Util.appVersion}")

        // proxy
        val proxy = api.proxy()
        proxy.registerRequestHandler(this)

    }

    override fun handleRequestReceived(interceptedRequest: InterceptedRequest?): ProxyRequestReceivedAction {
        val itr = Util.headers.iterator()
        var httpRequest: HttpRequest? = null
        while (itr.hasNext()){
            val header = itr.next()
            if (httpRequest == null) {
                httpRequest = interceptedRequest?.withRemovedHeader(header)
                httpRequest = httpRequest?.withHeader(header, Util.ipAddress)
            } else {
                httpRequest = httpRequest.withRemovedHeader(header)
                httpRequest = httpRequest?.withHeader(header, Util.ipAddress)
            }
        }
        return ProxyRequestReceivedAction.continueWith(httpRequest)
    }

    override fun handleRequestToBeSent(interceptedRequest: InterceptedRequest?): ProxyRequestToBeSentAction {
        return ProxyRequestToBeSentAction.continueWith(interceptedRequest)
    }
}