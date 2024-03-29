// hash: #c52d6e63
// @formatter:off

package metacode.core.lexer.data.models

import metacode.api.lexer.data.models.*

// Generated by BitBlock version 1.0.0

// @formatter:on
// @tomplot:customCode:start:B8CiSn
// @tomplot:customCode:end
// @formatter:off

data class DefaultOptionalLexerExpression(
	override val expression: LexerExpression,
	override val isGreedy: Boolean = true,
) : OptionalLexerExpression
{
	override fun equals(other: Any?): Boolean =
		isEqualTo(other)
	
	override fun hashCode(): Int =
		computeHashCode()
	
	override fun toString(): String =
		toJson()
	
	override fun duplicate(): DefaultOptionalLexerExpression =
		copy(
			expression = expression.duplicate(),
			isGreedy = isGreedy,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:fIpaBB
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
