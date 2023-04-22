package metacode.core.grammar

import metacode.core.lexer.HeadFirstLexer
import metacode.core.lexer.data.models.DefaultAnyOfLexerExpression
import metacode.core.lexer.data.models.DefaultCharacterGroupLexerExpression
import metacode.core.lexer.data.models.DefaultLexerRule
import metacode.core.lexer.data.models.DefaultLexerRuleMetaData
import metacode.core.lexer.data.models.DefaultLiteralLexerExpression
import metacode.core.lexer.data.models.DefaultOptionalLexerExpression
import metacode.core.lexer.data.models.DefaultRepeatingLexerExpression
import metacode.core.lexer.data.models.DefaultSequenceLexerExpression
import metacode.core.lexer.data.models.DefaultWildcardLexerExpression
import metacode.core.parser.utils.MetaCodeCharParser
import metacode.core.parser.utils.MetaCodeStringParser
import metacode.api.lexer.MetaCodeLexer
import metacode.api.lexer.data.models.AnyOfLexerExpression
import metacode.api.lexer.data.models.CharacterGroupLexerExpression
import metacode.api.lexer.data.models.LexerExpression
import metacode.api.lexer.data.models.LexerRule
import metacode.api.lexer.data.models.LexerRuleMetaData
import metacode.api.lexer.data.models.LiteralLexerExpression
import metacode.api.lexer.data.models.OptionalLexerExpression
import metacode.api.lexer.data.models.RepeatingLexerExpression
import metacode.api.lexer.data.models.SequenceLexerExpression
import metacode.api.lexer.data.models.WildcardLexerExpression
import metacode.api.lexer.sources.CodeSource
import metacode.api.parser.MetaCodeParser
import metacode.api.parser.results.ParserResult

fun parseLexer(source: CodeSource, lexer: MetaCodeLexer = metaCodeGrammarLexerLexer, parser: MetaCodeParser = metaCodeGrammarLexerParser): MetaCodeLexer
{
	val tokens = lexer
		.lex(source)
		.toList()

	val result = parser
		.parse("grammar", tokens)
		.firstOrNull()
		?: throw IllegalArgumentException("Cannot parse the lexer source code.")

	return result.buildLexer()
}

private fun ParserResult.buildLexer(): MetaCodeLexer =
	HeadFirstLexer(
		members["rules"].map { it.buildRule() }
	)

private fun ParserResult.buildRule(): LexerRule =
	DefaultLexerRule(
		members["name"].single().text,
		members["annotations"].buildMetaData(),
		members["expression"].single().buildExpression()
	)

private fun Collection<ParserResult>.buildMetaData(): LexerRuleMetaData
{
	val isSkippingRule = any { it.members["name"].single().text == "Skip" }

	return DefaultLexerRuleMetaData(
		isSkippingRule
	)
}

private fun ParserResult.buildExpression(): LexerExpression =
	when (option?.name)
	{
		"anyOfExpression" -> buildAnyOfExpression()
		"sequenceExpression" -> buildSequenceExpression()
		"optionalExpression" -> buildOptionalExpression()
		"repeatingExpression" -> buildRepeatingExpression()
		"enclosedExpression" -> buildEnclosedExpression()
		"literalExpression" -> buildLiteralExpression()
		"charGroupExpression" -> buildCharGroupExpression()
		"wildcardExpression" -> buildWildcardExpression()
		else -> throw IllegalArgumentException("Invalid option for lexer expression: ${option?.name}")
	}

private fun ParserResult.buildAnyOfExpression(): AnyOfLexerExpression =
	DefaultAnyOfLexerExpression(
		members["expressions"].map { it.buildExpression() }
	)

private fun ParserResult.buildSequenceExpression(): SequenceLexerExpression =
	DefaultSequenceLexerExpression(
		members["expressions"].map { it.buildExpression() }
	)

private fun ParserResult.buildOptionalExpression(): OptionalLexerExpression =
	DefaultOptionalLexerExpression(
		members["expression"].single().buildExpression()
	)

private fun ParserResult.buildRepeatingExpression(): RepeatingLexerExpression =
	DefaultRepeatingLexerExpression(
		members["expression"].single().buildExpression(),
		members["minimumMatches"].single().text.toInt()
	)

private fun ParserResult.buildEnclosedExpression(): LexerExpression =
	members["expression"].single().buildExpression()

private fun ParserResult.buildLiteralExpression(): LiteralLexerExpression =
	DefaultLiteralLexerExpression(
		members["value"].single().text.let { MetaCodeStringParser.parse(it) }
	)

private fun ParserResult.buildCharGroupExpression(): CharacterGroupLexerExpression
{
	// [^a-zA-Z0-9_\-\\\]]
	val value = members["value"].single().text

	val isPositive = value.startsWith("[^").not()

	// a-zA-Z0-9_\-\\\]
	val trimmed = value.substring(if (isPositive) 1 else 2, value.length - 1)

	// (?<left>[^\\\-]|\\.)(?:-(?<right>[^\\\-]|\\.))?
	val regex = Regex("(?<left>[^\\\\\\-]|\\\\.)(?:-(?<right>[^\\\\\\-]|\\\\.))?")

	val set = regex
		.findAll(trimmed)
		.flatMap {
			val left = it.groups["left"]!!
				.value
				.let(MetaCodeCharParser::parse)
			val right = it.groups["right"]
				?.value
				?.let(MetaCodeCharParser::parse)
				?: left

			(left..right)
				.asSequence()
		}
		.toSet()

	return DefaultCharacterGroupLexerExpression(
		set,
		isPositive
	)
}

private fun ParserResult.buildWildcardExpression(): WildcardLexerExpression =
	DefaultWildcardLexerExpression()
