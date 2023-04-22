// hash: #b8fd37a8
// @formatter:off

package metacode.core.lexer.data.builders

import metacode.api.lexer.data.models.*
import metacode.core.lexer.data.models.DefaultOptionalLexerExpression

// Generated by BitBlock version 1.0.0

// @formatter:on
// @tomplot:customCode:start:f5k3GB
// @tomplot:customCode:end
// @formatter:off

class OptionalLexerExpressionBuilder
{
	var expression: LexerExpression? = null
	
	var isGreedy: Boolean? = true
	
	fun build(): OptionalLexerExpression =
		DefaultOptionalLexerExpression(
			expression!!,
			isGreedy!!,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:0ETUWm
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
