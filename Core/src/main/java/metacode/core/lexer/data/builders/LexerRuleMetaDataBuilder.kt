// hash: #f80858c1
// @formatter:off

package metacode.core.lexer.data.builders

import metacode.api.lexer.data.models.*
import metacode.core.lexer.data.models.DefaultLexerRuleMetaData

// Generated by BitBlock version 1.0.0

// @formatter:on
// @tomplot:customCode:start:f5k3GB
// @tomplot:customCode:end
// @formatter:off

class LexerRuleMetaDataBuilder
{
	var isSkippingRule: Boolean? = false
	
	fun build(): LexerRuleMetaData =
		DefaultLexerRuleMetaData(
			isSkippingRule!!,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:0ETUWm
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
