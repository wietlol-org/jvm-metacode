// hash: #839a6444
// @formatter:off

package metacode.api.lexer.data.models

import bitblock.api.serialization.BitSerializable
import utils.common.Jsonable

// Generated by BitBlock version 1.0.0

// @formatter:on
// @tomplot:customCode:start:WTcTph

import metacode.api.lexer.sources.CharToken

// @tomplot:customCode:end
// @formatter:off

sealed interface LexerExpression : BitSerializable, Jsonable
{
	override fun duplicate(): LexerExpression
	
	// @formatter:on
	// @tomplot:customCode:start:0MOZ71
	
	fun findMatches(tokens: List<CharToken>, fromIndex: Int): Sequence<String>
	
	fun toExpressionString(): String
	
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
