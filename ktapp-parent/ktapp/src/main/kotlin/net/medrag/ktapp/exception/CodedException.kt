package net.medrag.ktapp.exception

/**
 * @author Stanislav Tretiakov
 * 17.07.2023
 */
class CodedException(val errorCode: ErrorCode) : RuntimeException(errorCode.message)