package metacode.core.grammar

import metacode.core.data.Grammar
import metacode.core.parser.HeadFirstRecursiveParser
import metacode.core.parser.data.models.DefaultLabelledParserExpression
import metacode.core.parser.data.models.DefaultLiteralParserExpression
import metacode.core.parser.data.models.DefaultPriorityTreeRule
import metacode.core.parser.data.models.DefaultPriorityTreeRuleOption
import metacode.core.parser.data.models.DefaultRepeatingParserExpression
import metacode.core.parser.data.models.DefaultRuleParserExpression
import metacode.core.parser.data.models.DefaultSequenceParserExpression
import metacode.core.parser.data.models.DefaultSimpleRule
import metacode.core.parser.data.models.DefaultTypeParserExpression
import metacode.api.parser.MetaCodeParser

private val grammar = DefaultSimpleRule(
	"grammar",
	DefaultSequenceParserExpression(listOf(
		DefaultLabelledParserExpression(
			"header",
			DefaultRuleParserExpression("grammarHeader")
		),
		DefaultRepeatingParserExpression(
			DefaultLabelledParserExpression(
				"rules",
				DefaultRuleParserExpression("rule")
			),
			0
		)
	))
)

private val grammarHeader = DefaultSimpleRule(
	"grammarHeader",
	DefaultSequenceParserExpression(listOf(
		DefaultLiteralParserExpression("grammar"),
		DefaultLabelledParserExpression(
			"name",
			DefaultTypeParserExpression("identifier")
		),
		DefaultLiteralParserExpression(";")
	))
)

private val rule = DefaultSimpleRule(
	"rule",
	DefaultSequenceParserExpression(listOf(
		DefaultRepeatingParserExpression(
			DefaultLabelledParserExpression(
				"annotations",
				DefaultSequenceParserExpression(
					listOf(
						DefaultLiteralParserExpression("@"),
						DefaultLabelledParserExpression(
							"name",
							DefaultTypeParserExpression("identifier")
						)
					)
				)
			),
			0
		),
		DefaultLabelledParserExpression(
			"name",
			DefaultTypeParserExpression("identifier")
		),
		DefaultLiteralParserExpression(":"),
		DefaultLabelledParserExpression(
			"expression",
			DefaultRuleParserExpression("expression")
		),
		DefaultLiteralParserExpression(";")
	))
)

private val expression = DefaultPriorityTreeRule(
	"expression",
	listOf(
		DefaultPriorityTreeRuleOption(
			"anyOfExpression",
			.9,
			DefaultSequenceParserExpression(listOf(
				DefaultLabelledParserExpression(
					"expressions",
					DefaultRuleParserExpression("expression", ">")
				),
				DefaultRepeatingParserExpression(
					DefaultSequenceParserExpression(listOf(
						DefaultLiteralParserExpression("|"),
						DefaultLabelledParserExpression(
							"expressions",
							DefaultRuleParserExpression("expression", ">")
						)
					)),
					1
				)
			))
		),
		DefaultPriorityTreeRuleOption(
			"sequenceExpression",
			.8,
			DefaultRepeatingParserExpression(
				DefaultLabelledParserExpression(
					"expressions",
					DefaultRuleParserExpression("expression", ">")
				),
				2
			)
		),
		DefaultPriorityTreeRuleOption(
			"optionalExpression",
			.7,
			DefaultSequenceParserExpression(listOf(
				DefaultLabelledParserExpression(
					"expression",
					DefaultRuleParserExpression("expression", ">")
				),
				DefaultLiteralParserExpression("?")
			))
		),
		DefaultPriorityTreeRuleOption(
			"repeatingExpression",
			.6,
			DefaultSequenceParserExpression(listOf(
				DefaultLabelledParserExpression(
					"expression",
					DefaultRuleParserExpression("expression", ">")
				),
				DefaultLiteralParserExpression("{"),
				DefaultLabelledParserExpression(
					"minimumMatches",
					DefaultTypeParserExpression("number")
				),
				DefaultLiteralParserExpression(","),
				DefaultLiteralParserExpression("}")
			))
		),
		DefaultPriorityTreeRuleOption(
			"enclosedExpression",
			.1,
			DefaultSequenceParserExpression(listOf(
				DefaultLiteralParserExpression("("),
				DefaultLabelledParserExpression(
					"expression",
					DefaultRuleParserExpression("expression")
				),
				DefaultLiteralParserExpression(")")
			))
		),
		DefaultPriorityTreeRuleOption(
			"literalExpression",
			.1,
			DefaultLabelledParserExpression(
				"value",
				DefaultTypeParserExpression("literal")
			)
		),
		DefaultPriorityTreeRuleOption(
			"charGroupExpression",
			.1,
			DefaultLabelledParserExpression(
				"value",
				DefaultTypeParserExpression("charGroup")
			)
		),
		DefaultPriorityTreeRuleOption(
			"wildcardExpression",
			.1,
			DefaultLiteralParserExpression(".")
		)
	)
)

val metaCodeGrammarLexerParser: MetaCodeParser = HeadFirstRecursiveParser(
	Grammar(
		"MetaCodeLexer",
		listOf(
			grammar,
			grammarHeader,
			rule,
			expression
		)
	)
)
