// hash: #270d5e0a
// @formatter:off

package metacode.core.lexer.data.models

import metacode.api.lexer.data.models.*

// Generated by BitBlock version 1.0.0

// @formatter:on
// @tomplot:customCode:start:B8CiSn
// @tomplot:customCode:end
// @formatter:off

data class DefaultLexerRuleMetaData(
	override val isSkippingRule: Boolean = false,
) : LexerRuleMetaData
{
	override fun equals(other: Any?): Boolean =
		isEqualTo(other)
	
	override fun hashCode(): Int =
		computeHashCode()
	
	override fun toString(): String =
		toJson()
	
	override fun duplicate(): DefaultLexerRuleMetaData =
		copy(
			isSkippingRule = isSkippingRule,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:fIpaBB
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
