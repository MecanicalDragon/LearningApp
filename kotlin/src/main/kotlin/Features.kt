import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

/**
 * @author Stanislav Tretiakov
 * 17.05.2023
 */
class NonNullValueIsNullException(
    message: String
) : RuntimeException(message)

@ExperimentalContracts
fun <T> T?.nonNull(message: String = "Nullable value was provided where non-null value must be.") : T  {
    contract {
        returns() implies (this@nonNull != null)
//        returnsNotNull()
    }
    return this ?: throw NonNullValueIsNullException(message)
}