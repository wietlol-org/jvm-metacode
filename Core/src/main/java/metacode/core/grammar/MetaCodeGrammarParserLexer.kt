package metacode.core.grammar

import metacode.api.lexer.MetaCodeLexer
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

private val identifier = DefaultSequenceLexerExpression(
	listOf(
		DefaultCharacterGroupLexerExpression(('a'..'z').plus('A'..'Z').toSet()),
		DefaultRepeatingLexerExpression(
			DefaultCharacterGroupLexerExpression(
				('a'..'z')
					.plus('A'..'Z')
					.plus('0'..'9')
					.plus('_')
					.toSet()),
			0
		)
	)
)

private val charGroup = DefaultSequenceLexerExpression(
	listOf(
		DefaultLiteralLexerExpression("["),
		DefaultOptionalLexerExpression(
			DefaultLiteralLexerExpression("^")
		),
		DefaultRepeatingLexerExpression(
			DefaultAnyOfLexerExpression(
				listOf(
					DefaultCharacterGroupLexerExpression(setOf('\\', ']'), false),
					DefaultSequenceLexerExpression(
						listOf(
							DefaultLiteralLexerExpression("\\"),
							DefaultWildcardLexerExpression()
						)
					)
				)
			),
			0,
			false
		),
		DefaultLiteralLexerExpression("]")
	)
)

private val literal = DefaultSequenceLexerExpression(
	listOf(
		DefaultLiteralLexerExpression("'"),
		DefaultRepeatingLexerExpression(
			DefaultAnyOfLexerExpression(
				listOf(
					DefaultCharacterGroupLexerExpression(setOf('\\', '\''), false),
					DefaultSequenceLexerExpression(
						listOf(
							DefaultLiteralLexerExpression("\\"),
							DefaultWildcardLexerExpression()
						)
					)
				)
			),
			0,
			false
		),
		DefaultLiteralLexerExpression("'")
	)
)

private val number = DefaultRepeatingLexerExpression(
	DefaultCharacterGroupLexerExpression(('0'..'9').toSet()),
	1
)

private val symbol = DefaultAnyOfLexerExpression(
	listOf(
		DefaultLiteralLexerExpression(":"),
		DefaultLiteralLexerExpression("|"),
		DefaultLiteralLexerExpression(";"),
		DefaultLiteralLexerExpression("?"),
		DefaultLiteralLexerExpression("("),
		DefaultLiteralLexerExpression(")"),
		DefaultLiteralLexerExpression("{"),
		DefaultLiteralLexerExpression("}"),
		DefaultLiteralLexerExpression(","),
		DefaultLiteralLexerExpression("."),
		DefaultLiteralLexerExpression("="),
		DefaultLiteralLexerExpression("@"),
		DefaultLiteralLexerExpression("&"),
		DefaultLiteralLexerExpression("#"),
		DefaultLiteralLexerExpression("->"),
		DefaultLiteralLexerExpression(">"),
		DefaultLiteralLexerExpression("<")
	)
)

private val whitespace = DefaultAnyOfLexerExpression(
	listOf(
		DefaultLiteralLexerExpression(" "),
		DefaultLiteralLexerExpression("\t"),
		DefaultLiteralLexerExpression("\r"),
		DefaultLiteralLexerExpression("\n")
	)
)

private val rules = listOf(
	DefaultLexerRule("identifier", DefaultLexerRuleMetaData(), identifier),
	DefaultLexerRule("literal", DefaultLexerRuleMetaData(), literal),
	DefaultLexerRule("number", DefaultLexerRuleMetaData(), number),
	DefaultLexerRule("symbol", DefaultLexerRuleMetaData(), symbol),
	DefaultLexerRule("whitespace", DefaultLexerRuleMetaData(true), whitespace)
)

val metaCodeGrammarParserLexer: MetaCodeLexer = HeadFirstLexer(rules)
