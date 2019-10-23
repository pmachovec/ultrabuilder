package com.pmachovec.ultrabuilder.taskmodels

import com.pmachovec.ultrabuilder.constants.GroupNames
import com.pmachovec.ultrabuilder.constants.NeededTaskNames
import com.pmachovec.ultrabuilder.constants.UltraBuilderTaskNames

class UltraTest : TaskModel {
    override val name = UltraBuilderTaskNames.ULTRATEST
    override val group = GroupNames.VERIFICATION
    override val description = "Runs " + NeededTaskNames.CLEANTEST + " and " + NeededTaskNames.TEST + " tasks."
}
