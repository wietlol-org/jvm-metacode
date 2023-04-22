package metacode.core.lexer

import metacode.api.lexer.MetaCodeLexer
import metacode.api.lexer.MetaCodeToken
import metacode.api.lexer.data.models.AnyOfLexerExpression
import metacode.api.lexer.data.models.CharacterGroupLexerExpression
import metacode.api.lexer.data.models.LexerExpression
import metacode.api.lexer.data.models.LexerRule
import metacode.api.lexer.data.models.LiteralLexerExpression
import metacode.api.lexer.data.models.OptionalLexerExpression
import metacode.api.lexer.data.models.RepeatingLexerExpression
import metacode.api.lexer.data.models.SequenceLexerExpression
import metacode.api.lexer.data.models.WildcardLexerExpression
import metacode.api.lexer.sources.CharToken
import metacode.api.lexer.sources.CodeSource
import utils.common.Jsonable
import utils.common.toJsonString

class HeadFirstLexer(
	val rules: List<LexerRule>
) : MetaCodeLexer, Jsonable
{
	override fun lex(source: CodeSource): Sequence<MetaCodeToken> =
		TokenReader(source)
			.use { it.readToList() }
			.let { findMatches(it, source) }

	override fun toJson(): String =
		"""{"rules":${rules.toJsonString()}}"""

	override fun toString(): String =
		toJson()

	private val headMap = rules
		.asSequence()
		.flatMap { rule -> rule.findHeads().map { Pair(it, rule) } }
		.groupBy({ it.first }, { it.second })
		.toMap()

	private fun LexerRule.findHeads(): Sequence<Char> =
		expression.findHeads()

	private fun LexerExpression.findHeads(): Sequence<Char> =
		when (this)
		{
			is AnyOfLexerExpression -> expressions.asSequence().flatMap { it.findHeads() }
			is CharacterGroupLexerExpression -> characters.asSequence() // fails for negative searches
			is LiteralLexerExpression -> sequenceOf(query.first())
			is OptionalLexerExpression -> expression.findHeads()
			is RepeatingLexerExpression -> expression.findHeads()
			is SequenceLexerExpression -> expressions
				.asSequence()
				.takeWhileInclusive { it is OptionalLexerExpression }
				.flatMap { it.findHeads() }
			is WildcardLexerExpression -> ('\u0000'..'\uFFFF').asSequence() // all characters
			else -> throw UnsupportedOperationException("Unsupported operation findHeads on type ${javaClass.name}.")
		}

	private fun <E> Sequence<E>.takeWhileInclusive(filter: (E) -> Boolean): Sequence<E> =
		sequence {
			forEach {
				yield(it)
				if (!filter(it))
					return@sequence
			}
		}

	private fun findMatches(tokens: List<CharToken>, source: CodeSource): Sequence<MetaCodeToken> =
		sequence {
			var index = 0

			while (index < tokens.size)
			{
				val token = tokens[index]
				val rules = headMap[token.char]
					?: throw IllegalArgumentException("Invalid character ${token.char} (${token.char.code.toString(16)}) found!")

				val match = rules
					.asSequence()
					.map { it.findInternalMatches(tokens, index, source) }
					.filterNotNull()
					.firstOrNull()
					?: throw IllegalArgumentException("Invalid character sequence starting with ${token.char} (${token.char.code.toString(16)}) found!")

				index += match.text.length

				if (match.rule.metaData.isSkippingRule.not())
					yield(match)
			}
		}

	private fun LexerRule.findInternalMatches(tokens: List<CharToken>, fromIndex: Int, source: CodeSource): RuleMetaCodeToken?
	{
		val headToken = tokens[fromIndex]

		return expression
			.findMatches(tokens, fromIndex)
			.map { RuleMetaCodeToken(name, it, headToken.line, headToken.column, source, this) }
			.firstOrNull()
	}

	private class RuleMetaCodeToken(
		override val type: String,
		override val text: String,
		override val line: Int,
		override val column: Int,
		override val source: CodeSource,
		val rule: LexerRule
	) : MetaCodeToken
	{
		override fun toString(): String =
			"""{"type": "MetaCodeToken", "tokenType": ${type.toJsonString()}, "text": ${text.toJsonString()}, "line": ${line.toJsonString()}, "column": ${column.toJsonString()}, "source": $source}"""
	}
}
