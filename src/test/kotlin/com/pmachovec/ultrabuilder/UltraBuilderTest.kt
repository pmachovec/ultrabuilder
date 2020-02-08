package com.pmachovec.ultrabuilder

import com.pmachovec.ultrabuilder.constants.NeededTaskNames
import com.pmachovec.ultrabuilder.constants.Texts
import com.pmachovec.ultrabuilder.constants.UltraBuilderTaskNames

import java.io.ByteArrayOutputStream
import java.io.PrintStream

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.powermock.modules.testng.PowerMockTestCase
import org.testng.Assert.assertNotNull
import org.testng.annotations.AfterClass
import org.testng.annotations.AfterMethod
import org.testng.annotations.BeforeClass
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test
import org.testng.Assert.assertEquals
import org.testng.Assert.assertNotEquals
import org.testng.Assert.assertTrue

class UltraBuilderTest : PowerMockTestCase() {
    private val testConsole = ByteArrayOutputStream()
    private val standardOutput = System.out
    private lateinit var project: Project

    @BeforeClass
    fun setupClass() {
        System.setOut(PrintStream(testConsole))
    }

    @BeforeMethod
    fun setupMethod() {
        project = ProjectBuilder.builder().build()
        project.pluginManager.apply(UltraBuilder::class.java)
    }

    @AfterMethod
    fun tearDownMethod() {
        testConsole.reset()
    }

    @AfterClass
    fun teardownClass() {
        System.setOut(standardOutput)
    }

    @Test(description = "Plugin available expected")
    fun pluginNotNullTest() {
        assertNotNull(project.plugins.findPlugin(UltraBuilder.NAME))
    }

    @Test(
        dependsOnMethods = ["pluginNotNullTest"],
        description = "Task ultraBuild available expected"
    )
    fun ultraBuildTaskAvailableTest() {
        taskAvailableTest(UltraBuilderTaskNames.ULTRABUILD)
    }

    @Test(
        dependsOnMethods = ["pluginNotNullTest"],
        description = "Task ultraTest available expected"
    )
    fun ultraTestTaskAvailableTest() {
        taskAvailableTest(UltraBuilderTaskNames.ULTRATEST)
    }

    @Test(
        dependsOnMethods = ["pluginNotNullTest", "ultraBuildTaskAvailableTest"],
        description = "Task ultraBuild, clean and build tasks not available, standard output info expected twice"
    )
    fun ultraBuildCleanBuildUnavailableTest() {
        taskStructureTest(UltraBuilderTaskNames.ULTRABUILD, listOf(), listOf(NeededTaskNames.CLEAN, NeededTaskNames.BUILD))
    }

    @Test(
        dependsOnMethods = ["pluginNotNullTest", "ultraBuildTaskAvailableTest"],
        description = "Task ultraBuild, clean task available, build task not available, ultraBuild depends on clean expected, standard output info expected once"
    )
    fun ultraBuildCleanAvailableBuildUnavailableTest() {
        taskStructureTest(UltraBuilderTaskNames.ULTRABUILD, listOf(NeededTaskNames.CLEAN), listOf(NeededTaskNames.BUILD))
    }

    @Test(
        dependsOnMethods = ["pluginNotNullTest", "ultraBuildTaskAvailableTest"],
        description = "Task ultraBuild, build task available, clean task not available, ultraBuild depends on build expected, standard output info expected once"
    )
    fun ultraBuildBuildAvailableCleanUnavailableTest() {
        taskStructureTest(UltraBuilderTaskNames.ULTRABUILD, listOf(NeededTaskNames.BUILD), listOf(NeededTaskNames.CLEAN))
    }

    @Test(
        dependsOnMethods = ["pluginNotNullTest", "ultraBuildTaskAvailableTest"],
        description = "Task ultraBuild, clean and build tasks available, ultraBuild depends on clean and build expected"
    )
    fun ultraBuildCleanBuildAvailableTest() {
        taskStructureTest(UltraBuilderTaskNames.ULTRABUILD, listOf(NeededTaskNames.CLEAN, NeededTaskNames.BUILD), listOf())
    }

    @Test(
        dependsOnMethods = ["pluginNotNullTest", "ultraTestTaskAvailableTest"],
        description = "Task ultraTest, cleanTest and test tasks not available, standard output info expected twice"
    )
    fun ultraTestCleanTestUnavailableTest() {
        taskStructureTest(UltraBuilderTaskNames.ULTRATEST, listOf(), listOf(NeededTaskNames.CLEANTEST, NeededTaskNames.TEST))
    }

    @Test(
        dependsOnMethods = ["pluginNotNullTest", "ultraTestTaskAvailableTest"],
        description = "Task ultraTest, cleanTest task available, test task not available, ultraTest depends on cleanTest expected, standard output info expected once"
    )
    fun ultraTestCleanAvailableTestUnavailableTest() {
        taskStructureTest(UltraBuilderTaskNames.ULTRATEST, listOf(NeededTaskNames.CLEANTEST), listOf(NeededTaskNames.TEST))
    }

    @Test(
        dependsOnMethods = ["pluginNotNullTest", "ultraTestTaskAvailableTest"],
        description = "Task ultraTest, test task available, cleanTest task not available, ultraTest depends on test expected, standard output info expected once"
    )
    fun ultraTestAvailableCleanUnavailableTest() {
        taskStructureTest(UltraBuilderTaskNames.ULTRATEST, listOf(NeededTaskNames.TEST), listOf(NeededTaskNames.CLEANTEST))
    }

    @Test(
        dependsOnMethods = ["pluginNotNullTest", "ultraTestTaskAvailableTest"],
        description = "Task ultraTest, cleanTest and test tasks available, ultraTest depends on cleanTest and test expected"
    )
    fun ultraTestCleanTestAvailableTest() {
        taskStructureTest(UltraBuilderTaskNames.ULTRATEST, listOf(NeededTaskNames.CLEANTEST, NeededTaskNames.TEST), listOf())
    }

    private fun taskAvailableTest(taskName: String) {
        val ultraBuilderTask = project.tasks.findByName(taskName)
        assertNotNull(ultraBuilderTask)
    }

    private fun taskStructureTest(taskName: String, availableTaskNames: List<String>, missingTaskNames: List<String>) {
        availableTaskNames.forEach {
            project.task(it)
        }

        project.pluginManager.apply(UltraBuilder::class.java)
        val ultraBuilderTaskSet = project.getTasksByName(taskName, false)
        assertEquals(1, ultraBuilderTaskSet.size)
        val ultraBuilderTask = ultraBuilderTaskSet.toList()[0]
        val ultraBuilderTaskDependencies = ultraBuilderTask!!.taskDependencies.getDependencies(ultraBuilderTask)
        assertEquals(ultraBuilderTaskDependencies.size, availableTaskNames.size)

        ultraBuilderTaskDependencies.forEach {
            assertTrue(availableTaskNames.contains(it.name))
        }

        ultraBuilderTask.actions.forEach {
            it.execute(ultraBuilderTask)
        }

        availableTaskNames.forEach {
            assertEquals(testConsole.toString().indexOf(Texts.TASK_NOT_AVAILABLE.format(it)), -1)
        }

        missingTaskNames.forEach {
            assertNotEquals(testConsole.toString().indexOf(Texts.TASK_NOT_AVAILABLE.format(it)), -1)
        }
    }
}
