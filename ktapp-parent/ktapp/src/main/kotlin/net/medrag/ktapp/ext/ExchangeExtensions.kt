package net.medrag.ktapp.ext

import org.springframework.http.HttpStatus
import org.springframework.web.reactive.HandlerMapping
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.util.pattern.PathPattern
import java.util.regex.Pattern

private const val SLASH = "/"
private const val URI_ROOT = "ROOT"
private const val URI_UNKNOWN = "UNKNOWN"
private const val URI_NOT_FOUND = "NOT_FOUND"
private const val URI_REDIRECTION = "REDIRECTION"

private val forwardSlashesPattern = Pattern.compile("//+")

internal fun ServerWebExchange.header(header: String): String? {
    val requestUidHeaders: List<String>? = request.headers[header]
    return if (requestUidHeaders.isNullOrEmpty()) null else requestUidHeaders[0]
}

internal fun ServerWebExchange.extractUri(): String {
    getAttribute<PathPattern>(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE)?.let {
        var patternString = it.patternString
        if (patternString.length > 1) {
            patternString = removeTrailingSlash(patternString)
        }
        return patternString.ifEmpty { URI_ROOT }
    }
    response.statusCode?.let {
        if (it.is3xxRedirection) {
            return URI_REDIRECTION
        }
        if (it == HttpStatus.NOT_FOUND) {
            return URI_NOT_FOUND
        }
    }
    val uri = request.path.value().ifBlank { SLASH }
    val singleSlashes = forwardSlashesPattern.matcher(uri).replaceAll(SLASH)
    return if (removeTrailingSlash(singleSlashes).isEmpty()) {
        URI_ROOT
    } else URI_UNKNOWN
}

private fun removeTrailingSlash(text: String): String {
    if (text.isEmpty()) {
        return text
    }
    return if (text.endsWith(SLASH)) text.substring(0, text.length - 1) else text
}
