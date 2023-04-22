package metacode.api.lexer

import metacode.api.lexer.sources.CodeSource
import utils.common.Jsonable
import utils.common.emptyHashCode
import utils.common.toJsonString
import utils.common.with

interface MetaCodeToken : Jsonable
{
	val type: String
	val text: String
	val line: Int
	val column: Int
	val source: CodeSource
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is MetaCodeToken) return false
		
		if (type != other.type) return false
		if (text != other.text) return false
		if (line != other.line) return false
		if (column != other.column) return false
		if (source != other.source) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(type)
			.with(text)
			.with(line)
			.with(column)
			.with(source)
	
	override fun toJson(): String =
		"""{"type":${type.toJsonString()},"text":${text.toJsonString()},"line":${line.toJsonString()},"column":${column.toJsonString()},"source":${source.toJson()}}"""
	
	companion object
	{
		fun of(type: String, text: String, line: Int, column: Int, source: CodeSource): MetaCodeToken =
			Implementation(type, text, line, column, source)
		
		private data class Implementation(
			override var type: String,
			override var text: String,
			override var line: Int,
			override var column: Int,
			override var source: CodeSource
		) : MetaCodeToken
		{
			override fun equals(other: Any?): Boolean =
				isEqualTo(other)
			
			override fun hashCode(): Int =
				computeHashCode()
			
			override fun toString(): String =
				toJson()
		}
	}
}
