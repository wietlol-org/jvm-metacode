// hash: #5c3ec2fe
// data: serializationKey:5ddb7ed1-4fb1-4cff-bc36-850cb0d0244b
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

interface LabelledParserExpression : BitSerializable, ParserExpression, Jsonable
{
	companion object
	{
		val serializationKey: UUID
			get() = UUID.fromString("5ddb7ed1-4fb1-4cff-bc36-850cb0d0244b")
	}
	
	override val serializationKey: UUID
		get() = Companion.serializationKey
	
	val label: String
	
	val expression: ParserExpression
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is LabelledParserExpression) return false
		
		if (label != other.label) return false
		if (expression != other.expression) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(label)
			.with(expression)
	
	override fun toJson(): String =
		"""{"label":${label.toJsonString()},"expression":${expression.toJsonString()}}"""
	
	override fun duplicate(): LabelledParserExpression
	
	// @formatter:on
	// @tomplot:customCode:start:32T3K8
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on