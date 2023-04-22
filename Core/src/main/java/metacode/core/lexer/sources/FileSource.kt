package metacode.core.lexer.sources

import metacode.api.lexer.sources.CodeSource
import utils.common.toJsonString
import java.io.File
import java.io.InputStream

class FileSource(
	val file: File
) : CodeSource
{
	val filePath: String
		get() = file.path
	
	override fun read(): InputStream =
		file.inputStream()
	
	override fun toJson(): String =
		"""{"filePath":${filePath.toJsonString()}}"""
	
	override fun toString(): String =
		toJson()
}
