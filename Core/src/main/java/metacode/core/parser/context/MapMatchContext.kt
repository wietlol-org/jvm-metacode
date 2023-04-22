package metacode.core.parser.context

import metacode.api.parser.context.MatchContext
import metacode.api.parser.data.models.ParserRule
import utils.common.toJsonString

class MapMatchContext(
	private val lookupTable: Map<String, ParserRule>
) : MatchContext
{
	override fun getRule(name: String): ParserRule =
		lookupTable[name.lowercase()]
			?: throw IllegalStateException("Rule ${name.toJsonString()} not found in the lookup table (${lookupTable.keys.map { it.toJsonString() }}).")
}
