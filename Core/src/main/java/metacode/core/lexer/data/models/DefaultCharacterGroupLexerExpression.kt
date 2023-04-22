// hash: #42242c27
// @formatter:off

package metacode.core.lexer.data.models

import metacode.api.lexer.data.models.*

// Generated by BitBlock version 1.0.0

// @formatter:on
// @tomplot:customCode:start:B8CiSn
// @tomplot:customCode:end
// @formatter:off

data class DefaultCharacterGroupLexerExpression(
	override val characters: Set<Char>,
	override val isPositive: Boolean = true,
) : CharacterGroupLexerExpression
{
	override fun equals(other: Any?): Boolean =
		isEqualTo(other)
	
	override fun hashCode(): Int =
		computeHashCode()
	
	override fun toString(): String =
		toJson()
	
	override fun duplicate(): DefaultCharacterGroupLexerExpression =
		copy(
			characters = characters.map { it }.toMutableSet(),
			isPositive = isPositive,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:fIpaBB
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on