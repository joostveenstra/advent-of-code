import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class InputFetcher(private val year: Int, private val day: Int) {
    fun execute(): String {
        val httpClient = HttpClient.newHttpClient()
        val httpRequest = HttpRequest.newBuilder(URI.create("https://adventofcode.com/$year/day/$day/input"))
            .header("Cookie", "session=" + SessionHelper.getSession())
            .build()
        val response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString())
        return response.body().trimEnd()
    }
}