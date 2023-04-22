// hash: #8e06c61b
// @formatter:off

package metacode.core.parser.data.models

import metacode.api.parser.data.models.*

// Generated by BitBlock version 1.0.0

// @formatter:on
// @tomplot:customCode:start:B8CiSn

import metacode.core.parser.data.commonMergeResults
import metacode.core.parser.results.ExpressionResultImpl
import metacode.api.lexer.MetaCodeToken
import metacode.api.parser.context.MatchContext
import metacode.api.parser.results.ExpressionResult
import metacode.api.parser.results.RuleResult
import utils.common.emptyManyValuesMap

// @tomplot:customCode:end
// @formatter:off

data class DefaultLiteralParserExpression(
	override val query: String,
) : LiteralParserExpression
{
	override fun equals(other: Any?): Boolean =
		isEqualTo(other)
	
	override fun hashCode(): Int =
		computeHashCode()
	
	override fun toString(): String =
		toJson()
	
	override fun duplicate(): DefaultLiteralParserExpression =
		copy(
			query = query,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:fIpaBB
	
	override fun findMatches(context: MatchContext, tokens: List<MetaCodeToken>, fromIndex: Int): Sequence<ExpressionResult>
	{
		if (tokens.size <= fromIndex)
			return emptySequence()
		
		val matchedTokens = loadTokens(tokens, fromIndex)
			?: return emptySequence()
		
		return if (matchedTokens.joinToString("") { it.text } == query)
			sequenceOf(
				ExpressionResultImpl(
					this,
					fromIndex,
					fromIndex + matchedTokens.size,
					matchedTokens,
					null,
					emptyManyValuesMap()
				)
			)
		else
			emptySequence()
	}
	
	override fun findMatches(context: MatchContext, tokens: List<MetaCodeToken>, fromIndex: Int, feed: List<RuleResult>): Sequence<ExpressionResult> =
		if (feed.isNotEmpty())
			throw IllegalStateException("Impossible to find matches with a feed on a non-left-recursive expression!")
		else
			findMatches(context, tokens, fromIndex)
	
	override fun isLeftRecursiveTo(context: MatchContext, name: CharSequence): Boolean =
		false
	
	override fun toGrammarString(): String =
		"'${query.replace("\\", "\\\\").replace("'", "\\'")}'"
	
	private fun loadTokens(tokens: List<MetaCodeToken>, fromIndex: Int): List<MetaCodeToken>?
	{
		var index = fromIndex
		var offset = 0
		val result = mutableListOf<MetaCodeToken>()
		var lastLength = 0
		var lastColumn: Int? = null
		var lastLine: Int? = null
		
		while (true)
		{
			if (tokens.size <= index)
				return null
			
			val currentToken = tokens[index]
			
			if (lastColumn != null)
				if (lastColumn + lastLength != currentToken.column || lastLine != currentToken.line)
					return null
			
			lastColumn = currentToken.column
			lastLine = currentToken.line
			lastLength = currentToken.text.length
			
			val requestedLength = query.length - offset
			val tokenLength = currentToken.text.length
			
			if (tokenLength > requestedLength)
				return null
			
			if (tokenLength == requestedLength)
				return if (currentToken.text == query.substring(offset))
				{
					result.add(currentToken)
					result
				}
				else
					null
			
			if (query.substring(offset).startsWith(currentToken.text).not())
				return null
			
			result.add(currentToken)
			
			index++
			offset += tokenLength
		}
	}
	
	override fun mergeResults(results: List<ExpressionResult>, tokens: List<MetaCodeToken>, fromIndex: Int): ExpressionResult =
		commonMergeResults(results, tokens, fromIndex)
	
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on