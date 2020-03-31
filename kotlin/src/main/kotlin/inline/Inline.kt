package inline

import java.util.concurrent.atomic.AtomicBoolean

/**
 * Functions acquires the lock on boolean before invocation and releases after.
 * Suppose this is not correct example of inline function.
 * @receiver AtomicBoolean
 * @param block Function0<Unit>
 * @return Boolean - was the lock acquired or not
 */
inline fun AtomicBoolean.lockForAction(block: () -> Unit): Boolean {
    if (this.compareAndSet(false, true)) {
        try {
            block.invoke()
        } finally {
            this.set(false)
            return true
        }
    } else return false
}