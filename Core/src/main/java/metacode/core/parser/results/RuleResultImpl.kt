package metacode.core.parser.results

import metacode.api.lexer.MetaCodeToken
import metacode.api.parser.data.models.ParserRule
import metacode.api.parser.data.models.RuleOption
import metacode.api.parser.results.ExpressionResult
import metacode.api.parser.results.RuleResult
import utils.common.collections.ManyValuesMap

data class RuleResultImpl(
	override val rule: ParserRule,
	override val startIndex: Int,
	override val endIndex: Int,
	override val tokens: List<MetaCodeToken>,
	override val option: RuleOption?,
	override val members: ManyValuesMap<CharSequence, ExpressionResult>,
) : RuleResult
{
	override fun toString(): String =
		toJson()
}
