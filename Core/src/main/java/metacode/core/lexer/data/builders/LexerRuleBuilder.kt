// hash: #c0750947
// @formatter:off

package metacode.core.lexer.data.builders

import metacode.api.lexer.data.models.*
import metacode.core.lexer.data.models.DefaultLexerRule

// Generated by BitBlock version 1.0.0

// @formatter:on
// @tomplot:customCode:start:f5k3GB
// @tomplot:customCode:end
// @formatter:off

class LexerRuleBuilder
{
	var name: String? = null
	
	var metaData: LexerRuleMetaData? = null
	
	var expression: LexerExpression? = null
	
	fun build(): LexerRule =
		DefaultLexerRule(
			name!!,
			metaData!!,
			expression!!,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:0ETUWm
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
