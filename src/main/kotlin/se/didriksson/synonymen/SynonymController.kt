package se.didriksson.synonymen

import org.jsoup.Jsoup
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@RestController
class SynonymController() {
	@GetMapping("/synonym/{synonym}")
	fun servaSynonym(@PathVariable synonym: String) = SynonymHTTPHamtaren.hamtaSynonymer(synonym)
}


class SynonymHTTPHamtaren() {
	companion object Factory {
		val url = "http://synonymer.se/?query="
		val synonymHTMLTag = "middlebanner"
		val exempelHTMLTag = "saol"

		fun hamtaSynonymer(ord: String): Pair<String, String> {
			val k = khttp.get(url + ord)
			val synonymer = if (!k.text.contains("Inga synonymer hittades för din sökning")) {
				Jsoup.parse(k.text)
						.getElementById(synonymHTMLTag)
						.getElementsByTag("a")
						.map { it.text() }
						.joinToString(separator = ", ", limit = 5, prefix = "<li>", postfix = "</li>")
			} else {
				return Pair("<li>Inga synonymer hittades för $ord</li>", "")
			}

			val exempelElement =
					if (k.text.contains("Hur används ordet ")) {
						"<li>"+ Jsoup.parse(k.text)
								.getElementById(exempelHTMLTag)
								.getElementsByTag("li")
								.first()
								.text() + "</li>"
					} else ""
			return Pair(synonymer, exempelElement)
		}
	}
}

