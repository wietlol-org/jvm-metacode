// hash: #0d9bf1ee
// data: serializationKey:d2d31394-8e74-4431-83da-a4bf1235088d
// @formatter:off

package metacode.api.lexer.data.models

import bitblock.api.serialization.BitSerializable
import java.util.UUID
import utils.common.Jsonable
import utils.common.emptyHashCode
import utils.common.toJsonString
import utils.common.with

// Generated by BitBlock version 1.0.0

// @formatter:on
// @tomplot:customCode:start:gAeCSq

import metacode.api.lexer.sources.CharToken
import utils.common.collections.NTreeNode

// @tomplot:customCode:end
// @formatter:off

interface RepeatingLexerExpression : BitSerializable, LexerExpression, Jsonable
{
	companion object
	{
		val serializationKey: UUID
			get() = UUID.fromString("d2d31394-8e74-4431-83da-a4bf1235088d")
	}
	
	override val serializationKey: UUID
		get() = Companion.serializationKey
	
	val expression: LexerExpression
	
	val minimumMatches: Int
	
	val isGreedy: Boolean
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is RepeatingLexerExpression) return false
		
		if (expression != other.expression) return false
		if (minimumMatches != other.minimumMatches) return false
		if (isGreedy != other.isGreedy) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(expression)
			.with(minimumMatches)
			.with(isGreedy)
	
	override fun toJson(): String =
		"""{"expression":${expression.toJsonString()},"minimumMatches":${minimumMatches.toJsonString()},"isGreedy":${isGreedy.toJsonString()}}"""
	
	override fun duplicate(): RepeatingLexerExpression
	
	// @formatter:on
	// @tomplot:customCode:start:32T3K8
	
	override fun findMatches(tokens: List<CharToken>, fromIndex: Int): Sequence<String> =
		sequenceOf(Unit)
			.flatMap { matchRepeatingRule(expression, tokens, fromIndex, NTreeNode(null)) }
			.map {
				it.sequenceToRoot()
					.filterNotNull()
					.toList()
			}
			.filter { it.size >= minimumMatches }
			.map { it.joinToString("") }
	
	override fun toExpressionString(): String =
		expression.toExpressionString() + "{$minimumMatches}"
	
	private fun matchRepeatingRule(rule: LexerExpression, tokens: List<CharToken>, fromIndex: Int, parentNode: NTreeNode<String?>): Sequence<NTreeNode<String?>> =
		if (isGreedy)
			matchRepeatingRuleGreedy(rule, tokens, fromIndex, parentNode)
		else
			matchRepeatingRuleLazy(rule, tokens, fromIndex, parentNode)
	
	private fun matchRepeatingRuleGreedy(rule: LexerExpression, tokens: List<CharToken>, fromIndex: Int, parentNode: NTreeNode<String?>): Sequence<NTreeNode<String?>> =
		rule
			.findMatches(tokens, fromIndex)
			.filter { it.isNotEmpty() }
			.map { NTreeNode(it, parentNode) }
			.flatMap { matchRepeatingRuleGreedy(rule, tokens, fromIndex + it.value!!.length, it) }
			.plus(parentNode)
	
	private fun matchRepeatingRuleLazy(rule: LexerExpression, tokens: List<CharToken>, fromIndex: Int, parentNode: NTreeNode<String?>): Sequence<NTreeNode<String?>> =
		sequenceOf(parentNode)
			.plus(rule
				.findMatches(tokens, fromIndex)
				.filter { it.isNotEmpty() }
				.map { NTreeNode(it, parentNode) }
				.flatMap { matchRepeatingRuleLazy(rule, tokens, fromIndex + it.value!!.length, it) })
	
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on