// hash: #9ec5c7c6
// data: serializationKey:847bf8cc-b48a-48ba-82e0-a03227305a4e
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

interface PriorityTreeRuleOption : BitSerializable, Jsonable
{
	companion object
	{
		val serializationKey: UUID
			get() = UUID.fromString("847bf8cc-b48a-48ba-82e0-a03227305a4e")
	}
	
	override val serializationKey: UUID
		get() = Companion.serializationKey
	
	val name: String
	
	val priority: Double
	
	val expression: ParserExpression
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is PriorityTreeRuleOption) return false
		
		if (name != other.name) return false
		if (priority != other.priority) return false
		if (expression != other.expression) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(name)
			.with(priority)
			.with(expression)
	
	override fun toJson(): String =
		"""{"name":${name.toJsonString()},"priority":${priority.toJsonString()},"expression":${expression.toJsonString()}}"""
	
	override fun duplicate(): PriorityTreeRuleOption
	
	// @formatter:on
	// @tomplot:customCode:start:32T3K8
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
