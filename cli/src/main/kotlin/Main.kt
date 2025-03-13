import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.main
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.int

internal class Cli : CliktCommand() {
    override fun run() = Unit
}

internal class Generate : CliktCommand() {
    private val year by argument("year", "Year").int()
    private val day by argument("day", "Day").int()

    override fun run() = FileHelper(year, day).createFiles()
}

internal class SetSession : CliktCommand(name = "set-session") {
    private val session by argument("session", "Session")

    override fun run() = SessionHelper.setSession(session)
}

fun main(args: Array<String>) = Cli().subcommands(Generate(), SetSession()).main(args)