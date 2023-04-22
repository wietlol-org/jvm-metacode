package metacode.core.lexer.sources

import metacode.api.lexer.sources.CodeSource
import utils.common.toJsonString
import java.io.InputStream

class StringSource(
	val name: String,
	private val text: String
) : CodeSource
{
	override fun read(): InputStream =
		text.byteInputStream()
	
	override fun toJson(): String =
		"""{"name":${name.toJsonString()}}"""
	
	override fun toString(): String =
		toJson()
}

