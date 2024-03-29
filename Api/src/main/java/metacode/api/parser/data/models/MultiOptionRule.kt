// hash: #1789b6eb
// data: serializationKey:50c065eb-a67a-491b-a467-32d946ba6586
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

interface MultiOptionRule : BitSerializable, ParserRule, Jsonable
{
	companion object
	{
		val serializationKey: UUID
			get() = UUID.fromString("50c065eb-a67a-491b-a467-32d946ba6586")
	}
	
	override val serializationKey: UUID
		get() = Companion.serializationKey
	
	override val name: String
	
	val options: List<RuleOption>
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is MultiOptionRule) return false
		
		if (name != other.name) return false
		if (options != other.options) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(name)
			.with(options)
	
	override fun toJson(): String =
		"""{"name":${name.toJsonString()},"options":${options.toJsonString()}}"""
	
	override fun duplicate(): MultiOptionRule
	
	// @formatter:on
	// @tomplot:customCode:start:32T3K8
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
