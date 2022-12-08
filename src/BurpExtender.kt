package burp

import java.util.*
import java.util.concurrent.CopyOnWriteArrayList

class BurpExtender: IBurpExtender, IProxyListener {
    companion object {
        var callback: IBurpExtenderCallbacks? = null
        var helper: IExtensionHelpers? = null
    }

    override fun registerExtenderCallbacks(callbacks: IBurpExtenderCallbacks?) {
        callback = callbacks
        callback!!.setExtensionName("${Util.appName} - ${Util.appVersion}")
        callback!!.printOutput("${Util.appName} - ${Util.appVersion}")
        helper = callback!!.helpers

        // register proxy listener
        callback!!.registerProxyListener(this)
    }

    override fun processProxyMessage(messageIsRequest: Boolean, message: IInterceptedProxyMessage?) {
        val messageInfo = message?.messageInfo

        if(messageIsRequest && messageInfo?.request!!.isNotEmpty()){
            updateHeaders(messageInfo)
        }
    }

    private fun updateHeaders(messageInfo: IHttpRequestResponse?){
        val requestDetails = helper!!.analyzeRequest(messageInfo!!.request)
        val headers = CopyOnWriteArrayList(requestDetails.headers)
        val messageBodyOffset = requestDetails.bodyOffset
        val msg = messageInfo.request
        val messageBody = helper!!.bytesToString(msg.slice(IntRange(messageBodyOffset, msg.size - 1)).toByteArray())

        // remove existing matching headers
        val itr = headers.iterator()
        val newHeaders = ArrayList<String>()
        while (itr.hasNext()){
            val header = itr.next()
            var includeHeader = true
            for (newHeader in CopyOnWriteArrayList(Util.headers)) {
                /*
                if (header.toLowerCase().contains(newHeader.toLowerCase())
                    || header.toLowerCase().contains(Util.origin.toLowerCase())) includeHeader = false
                 */
                if (header.toLowerCase().contains(newHeader.toLowerCase())) includeHeader = false
            }
            if (includeHeader) newHeaders.add(header)

            // Set origin header
            /*
            if (header.toLowerCase().contains(Util.origin.toLowerCase())){
                if (header.contains("ionic")) {
                    newHeaders.add(Util.originIonicPayload)
                }else {
                    newHeaders.add(Util.originPayload)
                }
            }
             */
        }

        // set new IP hacks
        for (header in Util.headers) {
            newHeaders.add("${header}: ${Util.ipAddress}")
        }

        // modify headers and send modified request
        messageInfo.request = helper!!.buildHttpMessage(newHeaders, messageBody.toByteArray())
    }
}