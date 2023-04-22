// hash: #036db0d4
// data: serializationKey:41d0d14b-f38e-4538-952b-c6b191e7e182
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

import metacode.api.parser.context.MatchContext

// @tomplot:customCode:end
// @formatter:off

interface RuleOption : BitSerializable, Jsonable
{
	companion object
	{
		val serializationKey: UUID
			get() = UUID.fromString("41d0d14b-f38e-4538-952b-c6b191e7e182")
	}
	
	override val serializationKey: UUID
		get() = Companion.serializationKey
	
	val name: String
	
	val expression: ParserExpression
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is RuleOption) return false
		
		if (name != other.name) return false
		if (expression != other.expression) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(name)
			.with(expression)
	
	override fun toJson(): String =
		"""{"name":${name.toJsonString()},"expression":${expression.toJsonString()}}"""
	
	override fun duplicate(): RuleOption
	
	// @formatter:on
	// @tomplot:customCode:start:32T3K8
	
	fun isLeftRecursiveTo(context: MatchContext, name: CharSequence): Boolean
	
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on