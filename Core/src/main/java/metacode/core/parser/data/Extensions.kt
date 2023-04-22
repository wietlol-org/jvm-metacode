package metacode.core.parser.data

import metacode.core.parser.results.ExpressionResultImpl
import metacode.api.lexer.MetaCodeToken
import metacode.api.parser.data.models.ParserExpression
import metacode.api.parser.results.ExpressionResult
import utils.common.collections.ManyValuesMap
import utils.common.emptyManyValuesMap

fun ParserExpression.commonMergeResults(results: List<ExpressionResult>, tokens: List<MetaCodeToken>, fromIndex: Int): ExpressionResult
{
	val startIndex = results.firstOrNull()?.startIndex ?: fromIndex
	val endIndex = results.lastOrNull()?.endIndex ?: startIndex
	val matchedTokens = tokens.subList(startIndex, endIndex)
	return ExpressionResultImpl(
		this,
		startIndex,
		endIndex,
		matchedTokens,
		null,
		results.asSequence().map { it.members }.fold(emptyManyValuesMap(), ManyValuesMap<CharSequence, ExpressionResult>::mergeWith)
	)
}
