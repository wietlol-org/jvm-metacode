package metacode.core.grammar

import metacode.api.parser.MetaCodeParser
import metacode.core.data.Grammar
import metacode.core.parser.HeadFirstRecursiveParser
import metacode.core.parser.data.models.DefaultAnyOfParserExpression
import metacode.core.parser.data.models.DefaultLabelledParserExpression
import metacode.core.parser.data.models.DefaultLiteralParserExpression
import metacode.core.parser.data.models.DefaultMultiOptionRule
import metacode.core.parser.data.models.DefaultOptionalParserExpression
import metacode.core.parser.data.models.DefaultPriorityTreeRule
import metacode.core.parser.data.models.DefaultPriorityTreeRuleOption
import metacode.core.parser.data.models.DefaultRepeatingParserExpression
import metacode.core.parser.data.models.DefaultRuleOption
import metacode.core.parser.data.models.DefaultRuleParserExpression
import metacode.core.parser.data.models.DefaultSequenceParserExpression
import metacode.core.parser.data.models.DefaultSimpleRule
import metacode.core.parser.data.models.DefaultTypeParserExpression

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

private val rule = DefaultMultiOptionRule(
	"rule",
	listOf(
		DefaultRuleOption(
			"singleOptionRule",
			DefaultSequenceParserExpression(listOf(
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
		),
		DefaultRuleOption(
			"multiOptionRule",
			DefaultSequenceParserExpression(listOf(
				DefaultLabelledParserExpression(
					"name",
					DefaultTypeParserExpression("identifier")
				),
				DefaultLiteralParserExpression(":"),
				DefaultLabelledParserExpression(
					"options",
					DefaultRuleParserExpression("ruleOption")
				),
				DefaultRepeatingParserExpression(
					DefaultSequenceParserExpression(listOf(
						DefaultLiteralParserExpression("|"),
						DefaultLabelledParserExpression(
							"options",
							DefaultRuleParserExpression("ruleOption")
						)
					)),
					0
				),
				DefaultLiteralParserExpression(";")
			))
		),
		DefaultRuleOption(
			"priorityTreeRule",
			DefaultSequenceParserExpression(listOf(
				DefaultLabelledParserExpression(
					"name",
					DefaultTypeParserExpression("identifier")
				),
				DefaultLiteralParserExpression(":"),
				DefaultLabelledParserExpression(
					"options",
					DefaultRuleParserExpression("priorityTreeRuleOption")
				),
				DefaultRepeatingParserExpression(
					DefaultSequenceParserExpression(listOf(
						DefaultLiteralParserExpression("|"),
						DefaultLabelledParserExpression(
							"options",
							DefaultRuleParserExpression("priorityTreeRuleOption")
						)
					)),
					0
				),
				DefaultLiteralParserExpression(";")
			))
		)
	)
)

private val ruleOption = DefaultSimpleRule(
	"ruleOption",
	DefaultSequenceParserExpression(
		listOf(
			DefaultLabelledParserExpression(
				"expression",
				DefaultRuleParserExpression("expression")
			),
			DefaultLiteralParserExpression("#"),
			DefaultLabelledParserExpression(
				"name",
				DefaultTypeParserExpression("identifier")
			)
		)
	)
)

private val priorityTreeRuleOption = DefaultSimpleRule(
	"priorityTreeRuleOption",
	DefaultSequenceParserExpression(
		listOf(
			DefaultLiteralParserExpression("."),
			DefaultLabelledParserExpression(
				"priority",
				DefaultTypeParserExpression("number")
			),
			DefaultLiteralParserExpression("->"),
			DefaultLabelledParserExpression(
				"expression",
				DefaultRuleParserExpression("expression")
			),
			DefaultLiteralParserExpression("#"),
			DefaultLabelledParserExpression(
				"name",
				DefaultTypeParserExpression("identifier")
			)
		)
	)
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
			"labelledExpression",
			.5,
			DefaultSequenceParserExpression(
				listOf(
					DefaultLabelledParserExpression(
						"label",
						DefaultTypeParserExpression("identifier")
					),
					DefaultLiteralParserExpression("="),
					DefaultLabelledParserExpression(
						"expression",
						DefaultRuleParserExpression("expression", ">")
					)
				)
			)
		),
		DefaultPriorityTreeRuleOption(
			"ruleExpression",
			.1,
			DefaultSequenceParserExpression(
				listOf(
					DefaultOptionalParserExpression(
						DefaultLabelledParserExpression(
							"prefix",
							DefaultAnyOfParserExpression(
								listOf(
									DefaultLiteralParserExpression("<"),
									DefaultLiteralParserExpression(">")
								)
							)
						)
					),
					DefaultLabelledParserExpression(
						"name",
						DefaultTypeParserExpression("identifier")
					)
				)
			)
		),
		DefaultPriorityTreeRuleOption(
			"typeExpression",
			.1,
			DefaultSequenceParserExpression(
				listOf(
					DefaultLiteralParserExpression("&"),
					DefaultLabelledParserExpression(
						"name",
						DefaultTypeParserExpression("identifier")
					)
				)
			)
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
			"wildcardExpression",
			.1,
			DefaultLiteralParserExpression(".")
		)
	)
)

val metaCodeGrammarParserParser: MetaCodeParser = HeadFirstRecursiveParser(
	Grammar(
		"MetaCodeParser",
		listOf(
			grammar,
			grammarHeader,
			rule,
			ruleOption,
			priorityTreeRuleOption,
			expression
		)
	)
)
