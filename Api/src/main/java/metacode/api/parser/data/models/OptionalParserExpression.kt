// hash: #30ebaada
// data: serializationKey:cc25bd63-be69-4d41-b72f-a2de5aae1b14
// @formatter:off

package metacode.api.parser.data.models

import bitblock.api.serialization.BitSerializable
import java.util.UUID
import utils.common.Jsonable
import utils.common.emptyHashCode
import utils.common.toJsonString
import utils.common.with

// Generated by BitBlock version 1.0.0

// @formatter:on
// @tomplot:customCode:start:gAeCSq
// @tomplot:customCode:end
// @formatter:off

interface OptionalParserExpression : BitSerializable, ParserExpression, Jsonable
{
	companion object
	{
		val serializationKey: UUID
			get() = UUID.fromString("cc25bd63-be69-4d41-b72f-a2de5aae1b14")
	}
	
	override val serializationKey: UUID
		get() = Companion.serializationKey
	
	val expression: ParserExpression
	
	val isGreedy: Boolean
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is OptionalParserExpression) return false
		
		if (expression != other.expression) return false
		if (isGreedy != other.isGreedy) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(expression)
			.with(isGreedy)
	
	override fun toJson(): String =
		"""{"expression":${expression.toJsonString()},"isGreedy":${isGreedy.toJsonString()}}"""
	
	override fun duplicate(): OptionalParserExpression
	
	// @formatter:on
	// @tomplot:customCode:start:32T3K8
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
