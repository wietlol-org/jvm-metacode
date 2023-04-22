package metacode.core

import unittest.core.models.TestModule
import unittest.core.models.TestOptions

open class LocalTestModule : TestModule
{
	override val options: TestOptions = TestOptions()
}
