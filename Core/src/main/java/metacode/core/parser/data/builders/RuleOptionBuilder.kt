// hash: #886822a2
// @formatter:off

package metacode.core.parser.data.builders

import metacode.api.parser.data.models.*
import metacode.core.parser.data.models.DefaultRuleOption

// Generated by BitBlock version 1.0.0

// @formatter:on
// @tomplot:customCode:start:f5k3GB
// @tomplot:customCode:end
// @formatter:off

class RuleOptionBuilder
{
	var name: String? = null
	
	var expression: ParserExpression? = null
	
	fun build(): RuleOption =
		DefaultRuleOption(
			name!!,
			expression!!,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:0ETUWm
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
