// hash: #2daa4251
// @formatter:off

package metacode.core.lexer.data.models

import metacode.api.lexer.data.models.*

// Generated by BitBlock version 1.0.0

// @formatter:on
// @tomplot:customCode:start:B8CiSn
// @tomplot:customCode:end
// @formatter:off

data class DefaultAnyOfLexerExpression(
	override val expressions: List<LexerExpression>,
) : AnyOfLexerExpression
{
	override fun equals(other: Any?): Boolean =
		isEqualTo(other)
	
	override fun hashCode(): Int =
		computeHashCode()
	
	override fun toString(): String =
		toJson()
	
	override fun duplicate(): DefaultAnyOfLexerExpression =
		copy(
			expressions = expressions.map { it.duplicate() }.toMutableList(),
		)
	
	// @formatter:on
	// @tomplot:customCode:start:fIpaBB
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on