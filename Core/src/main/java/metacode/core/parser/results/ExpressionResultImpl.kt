package metacode.core.parser.results

import metacode.api.lexer.MetaCodeToken
import metacode.api.parser.data.models.ParserExpression
import metacode.api.parser.data.models.RuleOption
import metacode.api.parser.results.ExpressionResult
import utils.common.collections.ManyValuesMap

class ExpressionResultImpl(
	override val expression: ParserExpression,
	override val startIndex: Int,
	override val endIndex: Int,
	override val tokens: List<MetaCodeToken>,
	override val option: RuleOption?,
	override val members: ManyValuesMap<CharSequence, ExpressionResult>,
) : ExpressionResult
{
	override fun toString(): String =
		toJson()
}
