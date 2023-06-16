
import burp.api.montoya.BurpExtension
import burp.api.montoya.MontoyaApi
import burp.api.montoya.logging.Logging
import burp.api.montoya.proxy.http.InterceptedRequest
import burp.api.montoya.proxy.http.ProxyRequestHandler
import burp.api.montoya.proxy.http.ProxyRequestReceivedAction
import burp.api.montoya.proxy.http.ProxyRequestToBeSentAction

class EspV2: BurpExtension, ProxyRequestHandler {
    private var logger: Logging? = null

    override fun initialize(api: MontoyaApi?) {
        // set extension name
        api!!.extension().setName(Util.appName)

        // logging
        logger = api.logging()

        // proxy
        val proxy = api.proxy()
        proxy.registerRequestHandler(this)

    }

    override fun handleRequestReceived(interceptedRequest: InterceptedRequest?): ProxyRequestReceivedAction {
        return ProxyRequestReceivedAction.continueWith(interceptedRequest?.withHeader("X-HTTP-Method-Override", "PUT"))
    }

    override fun handleRequestToBeSent(interceptedRequest: InterceptedRequest?): ProxyRequestToBeSentAction {
        return ProxyRequestToBeSentAction.continueWith(interceptedRequest)
    }
}