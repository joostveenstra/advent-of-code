package framework

class Context(override val input: String, override val isExample: Boolean) : Day {
    override val lines by lazy { input.lines() }
}