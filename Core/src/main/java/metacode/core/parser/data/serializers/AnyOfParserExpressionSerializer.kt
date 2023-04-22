// hash: #4e38e5b9
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
import metacode.api.parser.data.models.AnyOfParserExpression
import metacode.core.parser.data.builders.AnyOfParserExpressionBuilder
import metacode.core.parser.data.models.*
import utils.common.streams.readUnsignedVarInt
import utils.common.streams.writeUnsignedVarInt

// Generated by BitBlock version 1.0.0

// @formatter:on
// @tomplot:customCode:start:70v0f9
// @tomplot:customCode:end
// @formatter:off

object AnyOfParserExpressionSerializer : ModelSerializer<AnyOfParserExpression, AnyOfParserExpression>
{
	private val endOfObject: Int = 0
	
	private val expressionsIndex: Int = 1
	
	override val modelId: UUID
		get() = AnyOfParserExpression.serializationKey
	
	override val dataClass: Class<AnyOfParserExpression>
		get() = AnyOfParserExpression::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: AnyOfParserExpression)
	{
		stream.writeUnsignedVarInt(expressionsIndex)
		schema.serialize(serializationContext, stream, entity.expressions)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): AnyOfParserExpression
	{
		var expressions: MutableList<ParserExpression>? = mutableListOf()
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return DefaultAnyOfParserExpression(
					expressions!!.toMutableList(),
				)
				expressionsIndex -> expressions = schema.deserialize(deserializationContext, stream)
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