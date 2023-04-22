package metacode.api.parser.results

import metacode.api.parser.data.models.ParserExpression
import utils.common.toJsonString

interface ExpressionResult : ParserResult
{
	val expression: ParserExpression
	
	override fun toJson(): String =
		"""{"type": "ExpressionResult", "startIndex": "$startIndex", "endIndex": "$endIndex", "tokens": $tokens, "expression": $expression, "option": ${option?.toJson()}, "members": {${members.entries.joinToString(",") { "${it.key.toJsonString()}: ${it.value}" }}}}""".trimMargin()
}
