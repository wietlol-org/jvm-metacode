// hash: #faabb50c
// @formatter:off

package metacode.core.parser.data.models

import metacode.api.parser.data.models.*

// Generated by BitBlock version 1.0.0

// @formatter:on
// @tomplot:customCode:start:B8CiSn

import metacode.core.parser.results.RuleResultImpl
import metacode.api.lexer.MetaCodeToken
import metacode.api.parser.context.MatchContext
import metacode.api.parser.results.RuleResult
import utils.common.recursiveMapPostOrder
import java.util.*

// @tomplot:customCode:end
// @formatter:off

data class DefaultMultiOptionRule(
	override val name: String,
	override val options: List<RuleOption>,
) : MultiOptionRule
{
	override fun equals(other: Any?): Boolean =
		isEqualTo(other)
	
	override fun hashCode(): Int =
		computeHashCode()
	
	override fun toString(): String =
		toJson()
	
	override fun duplicate(): DefaultMultiOptionRule =
		copy(
			name = name,
			options = options.map { it.duplicate() }.toMutableList(),
		)
	
	// @formatter:on
	// @tomplot:customCode:start:fIpaBB
	
	override fun findMatches(context: MatchContext, tokens: List<MetaCodeToken>, fromIndex: Int): Sequence<RuleResult> =
		getNonLeftRecursiveOptions(context)
			.asSequence()
			.flatMap { option ->
				option.expression.findMatches(context, tokens, fromIndex)
					.map { Pair(option, it) }
			}
			.map { (option, result) -> Pair(result, RuleResultImpl(this, result.startIndex, result.endIndex, result.tokens, option, result.members)) }
			.recursiveMapPostOrder { (_, ruleResult) ->
				getLeftRecursiveOptions(context)
					.asSequence()
					.flatMap { option ->
						option.expression.findMatches(context, tokens, ruleResult.endIndex, listOf(ruleResult))
							.map { Pair(option, it) }
					}
					.map { (option, result) -> Pair(result, RuleResultImpl(this, result.startIndex, result.endIndex, result.tokens, option, result.members)) }
			}
			.map { it.second }
			.map { RuleResultImpl(this, it.startIndex, it.endIndex, it.tokens, it.option, it.members) }
	
	override fun isLeftRecursiveTo(context: MatchContext, name: CharSequence): Boolean =
		options.any { it.expression.isLeftRecursiveTo(context, this.name).not() && it.expression.isLeftRecursiveTo(context, name) }
	
	private val leftRecursiveOptionsMap: MutableMap<MultiOptionRule, List<RuleOption>> = IdentityHashMap()
	private fun MultiOptionRule.getLeftRecursiveOptions(context: MatchContext): List<RuleOption> =
		leftRecursiveOptionsMap.computeIfAbsent(this) { options.filter { it.isLeftRecursiveTo(context, this.name) } }
	
	private val nonLeftRecursiveOptionsMap: MutableMap<MultiOptionRule, List<RuleOption>> = IdentityHashMap()
	private fun MultiOptionRule.getNonLeftRecursiveOptions(context: MatchContext): List<RuleOption> =
		nonLeftRecursiveOptionsMap.computeIfAbsent(this) { options.filter { it.isLeftRecursiveTo(context, this.name).not() } }
	
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
