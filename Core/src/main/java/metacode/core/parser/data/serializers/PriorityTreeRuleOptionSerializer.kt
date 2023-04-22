// hash: #331c2b64
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
import metacode.api.parser.data.models.PriorityTreeRuleOption
import metacode.core.parser.data.builders.PriorityTreeRuleOptionBuilder
import metacode.core.parser.data.models.*
import utils.common.streams.readUnsignedVarInt
import utils.common.streams.writeUnsignedVarInt

// Generated by BitBlock version 1.0.0

// @formatter:on
// @tomplot:customCode:start:70v0f9
// @tomplot:customCode:end
// @formatter:off

object PriorityTreeRuleOptionSerializer : ModelSerializer<PriorityTreeRuleOption, PriorityTreeRuleOption>
{
	private val endOfObject: Int = 0
	
	private val nameIndex: Int = 1
	
	private val priorityIndex: Int = 2
	
	private val expressionIndex: Int = 3
	
	override val modelId: UUID
		get() = PriorityTreeRuleOption.serializationKey
	
	override val dataClass: Class<PriorityTreeRuleOption>
		get() = PriorityTreeRuleOption::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: PriorityTreeRuleOption)
	{
		stream.writeUnsignedVarInt(nameIndex)
		schema.serialize(serializationContext, stream, entity.name)
		
		stream.writeUnsignedVarInt(priorityIndex)
		schema.serialize(serializationContext, stream, entity.priority)
		
		stream.writeUnsignedVarInt(expressionIndex)
		schema.serialize(serializationContext, stream, entity.expression)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): PriorityTreeRuleOption
	{
		var name: String? = null
		var priority: Double? = null
		var expression: ParserExpression? = null
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return DefaultPriorityTreeRuleOption(
					name!!,
					priority!!,
					expression!!,
				)
				nameIndex -> name = schema.deserialize(deserializationContext, stream)
				priorityIndex -> priority = schema.deserialize(deserializationContext, stream)
				expressionIndex -> expression = schema.deserialize(deserializationContext, stream)
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
