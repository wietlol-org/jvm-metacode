package metacode.core.lexer.sources

import metacode.api.lexer.sources.CodeSource
import utils.common.toJsonString
import java.io.InputStream

class StreamSource(
	val name: String,
	private val stream: InputStream
) : CodeSource
{
	override fun read(): InputStream =
		stream
	
	override fun toJson(): String =
		"""{"name":${name.toJsonString()}}"""
	
	override fun toString(): String =
		toJson()
}
