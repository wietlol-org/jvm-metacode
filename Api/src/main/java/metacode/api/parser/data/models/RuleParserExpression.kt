// hash: #a699383b
// data: serializationKey:b6100b3b-6f01-4077-b4db-1236a90de94b
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

interface RuleParserExpression : BitSerializable, ParserExpression, Jsonable
{
	companion object
	{
		val serializationKey: UUID
			get() = UUID.fromString("b6100b3b-6f01-4077-b4db-1236a90de94b")
	}
	
	override val serializationKey: UUID
		get() = Companion.serializationKey
	
	val ruleName: String
	
	val prefix: String?
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is RuleParserExpression) return false
		
		if (ruleName != other.ruleName) return false
		if (prefix != other.prefix) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(ruleName)
			.with(prefix)
	
	override fun toJson(): String =
		"""{"ruleName":${ruleName.toJsonString()},"prefix":${prefix.toJsonString()}}"""
	
	override fun duplicate(): RuleParserExpression
	
	// @formatter:on
	// @tomplot:customCode:start:32T3K8
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
