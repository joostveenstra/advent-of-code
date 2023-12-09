package framework

import org.junit.jupiter.api.DisplayNameGenerator
import java.lang.reflect.Method
import java.util.*

internal class FullyQualified: DisplayNameGenerator.Standard() {
    override fun generateDisplayNameForClass(testClass: Class<*>): String {
        return testClass.name
    }

    override fun generateDisplayNameForMethod(testClass: Class<*>, testMethod: Method): String {
        return testMethod.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    }
}