// hash: #a919860a
// @formatter:off

package metacode.core.parser.data

import bitblock.api.registry.ModelRegistry
import bitblock.api.registry.ModelRegistryKey
import bitblock.api.registry.RegistrySet
import bitblock.api.serialization.ModelSerializer
import bitblock.core.BitBlockBase
import bitblock.core.registry.CommonModelRegistryKey
import metacode.core.parser.data.serializers.*

// Generated by BitBlock version 1.0.0

object MetaCodeParser : RegistrySet
{
	override val modelSerializers: Map<ModelRegistryKey, ModelSerializer<*, *>> = createModelSerializers()
	
	private fun createModelSerializers(): Map<ModelRegistryKey, ModelSerializer<*, *>> =
		mapOf(
			CommonModelRegistryKey("SimpleRule", "MetaCodeParser", "Wietlol", "1.0") to SimpleRuleSerializer,
			CommonModelRegistryKey("MultiOptionRule", "MetaCodeParser", "Wietlol", "1.0") to MultiOptionRuleSerializer,
			CommonModelRegistryKey("RuleOption", "MetaCodeParser", "Wietlol", "1.0") to RuleOptionSerializer,
			CommonModelRegistryKey("PriorityTreeRule", "MetaCodeParser", "Wietlol", "1.0") to PriorityTreeRuleSerializer,
			CommonModelRegistryKey("PriorityTreeRuleOption", "MetaCodeParser", "Wietlol", "1.0") to PriorityTreeRuleOptionSerializer,
			CommonModelRegistryKey("AnyOfParserExpression", "MetaCodeParser", "Wietlol", "1.0") to AnyOfParserExpressionSerializer,
			CommonModelRegistryKey("SequenceParserExpression", "MetaCodeParser", "Wietlol", "1.0") to SequenceParserExpressionSerializer,
			CommonModelRegistryKey("LabelledParserExpression", "MetaCodeParser", "Wietlol", "1.0") to LabelledParserExpressionSerializer,
			CommonModelRegistryKey("OptionalParserExpression", "MetaCodeParser", "Wietlol", "1.0") to OptionalParserExpressionSerializer,
			CommonModelRegistryKey("RepeatingParserExpression", "MetaCodeParser", "Wietlol", "1.0") to RepeatingParserExpressionSerializer,
			CommonModelRegistryKey("RuleParserExpression", "MetaCodeParser", "Wietlol", "1.0") to RuleParserExpressionSerializer,
			CommonModelRegistryKey("TypeParserExpression", "MetaCodeParser", "Wietlol", "1.0") to TypeParserExpressionSerializer,
			CommonModelRegistryKey("LiteralParserExpression", "MetaCodeParser", "Wietlol", "1.0") to LiteralParserExpressionSerializer,
			CommonModelRegistryKey("WildcardParserExpression", "MetaCodeParser", "Wietlol", "1.0") to WildcardParserExpressionSerializer,
		)
	
	override fun initialize(registry: ModelRegistry?) =
		modelSerializers.forEach((registry ?: BitBlockBase.modelRegistry)::set)
}
// @formatter:on
