package metacode.api.parser.context

import metacode.api.parser.data.models.ParserRule

interface MatchContext
{
	fun getRule(name: String): ParserRule
}
