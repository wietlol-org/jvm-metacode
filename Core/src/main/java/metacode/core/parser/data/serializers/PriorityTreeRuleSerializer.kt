// hash: #1ad00292
// @formatter:off

package metacode.core.parser.data.serializers

import bitblock.api.serialization.DeserializationContext
import bitblock.api.serialization.ModelSerializer
import bitblock.api.serialization.Schema
import bitblock.api.serialization.SerializationContext
import bitblock.api.serialization.deserialize
import java.io.InputStream
import java.io.OutputStream
import java.util.UUID
import metacode.api.parser.data.models.*
import metacode.api.parser.data.models.PriorityTreeRule
import metacode.core.parser.data.builders.PriorityTreeRuleBuilder
import metacode.core.parser.data.models.*
import utils.common.streams.readUnsignedVarInt
import utils.common.streams.writeUnsignedVarInt

// Generated by BitBlock version 1.0.0

// @formatter:on
// @tomplot:customCode:start:70v0f9
// @tomplot:customCode:end
// @formatter:off

object PriorityTreeRuleSerializer : ModelSerializer<PriorityTreeRule, PriorityTreeRule>
{
	private val endOfObject: Int = 0
	
	private val nameIndex: Int = 1
	
	private val optionsIndex: Int = 2
	
	override val modelId: UUID
		get() = PriorityTreeRule.serializationKey
	
	override val dataClass: Class<PriorityTreeRule>
		get() = PriorityTreeRule::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: PriorityTreeRule)
	{
		stream.writeUnsignedVarInt(nameIndex)
		schema.serialize(serializationContext, stream, entity.name)
		
		stream.writeUnsignedVarInt(optionsIndex)
		schema.serialize(serializationContext, stream, entity.options)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): PriorityTreeRule
	{
		var name: String? = null
		var options: MutableList<PriorityTreeRuleOption>? = mutableListOf()
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return DefaultPriorityTreeRule(
					name!!,
					options!!.toMutableList(),
				)
				nameIndex -> name = schema.deserialize(deserializationContext, stream)
				optionsIndex -> options = schema.deserialize(deserializationContext, stream)
				else -> schema.deserialize<Any>(deserializationContext, stream)
			}
		}
	}
	
	// @formatter:on
	// @tomplot:customCode:start:5CFs54
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
