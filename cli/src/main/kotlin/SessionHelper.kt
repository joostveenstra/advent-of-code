import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.io.path.writeText

internal object SessionHelper {
    private val sessionPath = Path("session/session.txt")

    fun getSession() = sessionPath.readText()

    fun setSession(value: String) = sessionPath.writeText(value)
}