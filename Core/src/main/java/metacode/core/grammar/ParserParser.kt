package metacode.core.grammar

import metacode.core.data.Grammar
import metacode.core.parser.HeadFirstRecursiveParser
import metacode.core.parser.data.models.*
import metacode.core.parser.utils.MetaCodeStringParser
import metacode.api.lexer.MetaCodeLexer
import metacode.api.lexer.sources.CodeSource
import metacode.api.parser.MetaCodeParser
import metacode.api.parser.data.models.*
import metacode.api.parser.results.ParserResult

fun parseParser(source: CodeSource, lexer: MetaCodeLexer = metaCodeGrammarParserLexer, parser: MetaCodeParser = metaCodeGrammarParserParser): MetaCodeParser
{
	val tokens = lexer
		.lex(source)
		.toList()

	val result = parser
		.parseExhaustive("grammar", tokens)
		.firstOrNull()
		?: throw IllegalArgumentException("Cannot parse the parser source code.")

	return result.buildParser()
}

private fun ParserResult.buildParser(): MetaCodeParser =
	HeadFirstRecursiveParser(Grammar(
		members["header"].single().members["name"].single().text,
		members["rules"].map { it.buildRule() }
	))

private fun ParserResult.buildRule(): ParserRule =
	when (option?.name)
	{
		"singleOptionRule" -> buildSingleOptionRule()
		"multiOptionRule" -> buildMultiOptionRule()
		"priorityTreeRule" -> buildPriorityTreeRule()
		else -> throw IllegalArgumentException("Invalid option for parser rule: ${option?.name}")
	}

private fun ParserResult.buildSingleOptionRule(): SimpleRule =
	DefaultSimpleRule(
		members["name"].single().text,
		members["expression"].single().buildExpression()
	)

private fun ParserResult.buildMultiOptionRule(): MultiOptionRule =
	DefaultMultiOptionRule(
		members["name"].single().text,
		members["options"].map { it.buildRuleOption() }
	)

private fun ParserResult.buildRuleOption(): RuleOption =
	DefaultRuleOption(
		members["name"].single().text,
		members["expression"].single().buildExpression()
	)

private fun ParserResult.buildPriorityTreeRule(): PriorityTreeRule =
	DefaultPriorityTreeRule(
		members["name"].single().text,
		members["options"].map { it.buildPriorityTreeRuleOption() }
	)

private fun ParserResult.buildPriorityTreeRuleOption(): PriorityTreeRuleOption =
	DefaultPriorityTreeRuleOption(
		members["name"].single().text,
		members["priority"].single().text.let { "0.$it" }.toDouble(),
		members["expression"].single().buildExpression()
	)

private fun ParserResult.buildExpression(): ParserExpression =
	when (option?.name)
	{
		"anyOfExpression" -> buildAnyOfExpression()
		"sequenceExpression" -> buildSequenceExpression()
		"optionalExpression" -> buildOptionalExpression()
		"repeatingExpression" -> buildRepeatingExpression()
		"labelledExpression" -> buildLabelledExpression()
		"ruleExpression" -> buildRuleExpression()
		"typeExpression" -> buildTypeExpression()
		"enclosedExpression" -> buildEnclosedExpression()
		"literalExpression" -> buildLiteralExpression()
		"wildcardExpression" -> buildWildcardExpression()
		else -> throw IllegalArgumentException("Invalid option for parser expression: ${option?.name}")
	}

private fun ParserResult.buildAnyOfExpression(): AnyOfParserExpression =
	DefaultAnyOfParserExpression(
		members["expressions"].map { it.buildExpression() }
	)

private fun ParserResult.buildSequenceExpression(): SequenceParserExpression =
	DefaultSequenceParserExpression(
		members["expressions"].map { it.buildExpression() }
	)

private fun ParserResult.buildOptionalExpression(): OptionalParserExpression =
	DefaultOptionalParserExpression(
		members["expression"].single().buildExpression()
	)

private fun ParserResult.buildRepeatingExpression(): RepeatingParserExpression =
	DefaultRepeatingParserExpression(
		members["expression"].single().buildExpression(),
		members["minimumMatches"].single().text.toInt()
	)

private fun ParserResult.buildLabelledExpression(): LabelledParserExpression =
	DefaultLabelledParserExpression(
		members["label"].single().text,
		members["expression"].single().buildExpression()
	)

private fun ParserResult.buildRuleExpression(): RuleParserExpression =
	DefaultRuleParserExpression(
		members["name"].single().text,
		members["prefix"].singleOrNull()?.text
	)

private fun ParserResult.buildTypeExpression(): TypeParserExpression =
	DefaultTypeParserExpression(
		members["name"].single().text
	)

private fun ParserResult.buildEnclosedExpression(): ParserExpression =
	members["expression"].single().buildExpression()

private fun ParserResult.buildLiteralExpression(): LiteralParserExpression =
	DefaultLiteralParserExpression(
		members["value"].single().text.let { MetaCodeStringParser.parse(it) }
	)

private fun buildWildcardExpression(): WildcardParserExpression =
	DefaultWildcardParserExpression()
