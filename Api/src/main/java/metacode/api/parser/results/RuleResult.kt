package metacode.api.parser.results

import metacode.api.parser.data.models.ParserRule
import utils.common.toJsonString

interface RuleResult : ParserResult
{
	val rule: ParserRule
	
	override fun toJson(): String =
		"""{"type": "RuleResult", "startIndex": "$startIndex", "endIndex": "$endIndex", "tokens": $tokens, "rule": $rule, "option": ${option.toJsonString()}, "members": {${members.entries.joinToString(",") { "${it.key.toJsonString()}: ${it.value}" }}}}""".trimMargin()
}
