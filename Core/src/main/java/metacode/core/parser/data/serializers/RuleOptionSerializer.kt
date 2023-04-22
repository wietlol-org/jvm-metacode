// hash: #c97574a6
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
import metacode.api.parser.data.models.RuleOption
import metacode.core.parser.data.builders.RuleOptionBuilder
import metacode.core.parser.data.models.*
import utils.common.streams.readUnsignedVarInt
import utils.common.streams.writeUnsignedVarInt

// Generated by BitBlock version 1.0.0

// @formatter:on
// @tomplot:customCode:start:70v0f9
// @tomplot:customCode:end
// @formatter:off

object RuleOptionSerializer : ModelSerializer<RuleOption, RuleOption>
{
	private val endOfObject: Int = 0
	
	private val nameIndex: Int = 1
	
	private val expressionIndex: Int = 2
	
	override val modelId: UUID
		get() = RuleOption.serializationKey
	
	override val dataClass: Class<RuleOption>
		get() = RuleOption::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: RuleOption)
	{
		stream.writeUnsignedVarInt(nameIndex)
		schema.serialize(serializationContext, stream, entity.name)
		
		stream.writeUnsignedVarInt(expressionIndex)
		schema.serialize(serializationContext, stream, entity.expression)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): RuleOption
	{
		var name: String? = null
		var expression: ParserExpression? = null
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return DefaultRuleOption(
					name!!,
					expression!!,
				)
				nameIndex -> name = schema.deserialize(deserializationContext, stream)
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
