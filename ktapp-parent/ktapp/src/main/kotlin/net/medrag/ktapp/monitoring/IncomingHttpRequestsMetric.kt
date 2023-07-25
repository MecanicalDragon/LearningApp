package net.medrag.ktapp.monitoring

enum class IncomingHttpRequestsMetric(override val metricName: String, override val description: String) : Metric {
    INCOMING_HTTP_REQUESTS("incoming_http_requests", "Incoming HTTP requests")
}