// hash: #4eaba4b9
// @formatter:off

package metacode.core.lexer.data.serializers

import bitblock.api.serialization.DeserializationContext
import bitblock.api.serialization.ModelSerializer
import bitblock.api.serialization.Schema
import bitblock.api.serialization.SerializationContext
import bitblock.api.serialization.deserialize
import java.io.InputStream
import java.io.OutputStream
import java.util.UUID
import metacode.api.lexer.data.models.*
import metacode.api.lexer.data.models.LiteralLexerExpression
import metacode.core.lexer.data.builders.LiteralLexerExpressionBuilder
import metacode.core.lexer.data.models.*
import utils.common.streams.readUnsignedVarInt
import utils.common.streams.writeUnsignedVarInt

// Generated by BitBlock version 1.0.0

// @formatter:on
// @tomplot:customCode:start:70v0f9
// @tomplot:customCode:end
// @formatter:off

object LiteralLexerExpressionSerializer : ModelSerializer<LiteralLexerExpression, LiteralLexerExpression>
{
	private val endOfObject: Int = 0
	
	private val queryIndex: Int = 1
	
	override val modelId: UUID
		get() = LiteralLexerExpression.serializationKey
	
	override val dataClass: Class<LiteralLexerExpression>
		get() = LiteralLexerExpression::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: LiteralLexerExpression)
	{
		stream.writeUnsignedVarInt(queryIndex)
		schema.serialize(serializationContext, stream, entity.query)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): LiteralLexerExpression
	{
		var query: String? = null
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return DefaultLiteralLexerExpression(
					query!!,
				)
				queryIndex -> query = schema.deserialize(deserializationContext, stream)
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
