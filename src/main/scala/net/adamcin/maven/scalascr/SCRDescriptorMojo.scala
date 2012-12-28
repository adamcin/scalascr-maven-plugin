/*
 * This is free and unencumbered software released into the public domain.
 *
 * Anyone is free to copy, modify, publish, use, compile, sell, or
 * distribute this software, either in source code form or as a compiled
 * binary, for any purpose, commercial or non-commercial, and by any
 * means.
 *
 * In jurisdictions that recognize copyright laws, the author or authors
 * of this software dedicate any and all copyright interest in the
 * software to the public domain. We make this dedication for the benefit
 * of the public at large and to the detriment of our heirs and
 * successors. We intend this dedication to be an overt act of
 * relinquishment in perpetuity of all present and future rights to this
 * software under copyright law.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 *
 * For more information, please refer to <http://unlicense.org/>
 */

package net.adamcin.maven.scalascr

import org.apache.maven.plugin.{MojoFailureException, MojoExecutionException, AbstractMojo}
import org.apache.maven.plugins.annotations._
import org.apache.maven.artifact.versioning.DefaultArtifactVersion
import org.apache.maven.project.MavenProject
import org.osgi.service.component.ComponentConstants
import scala.Some
import scala.collection.JavaConversions._
import java.io.File
import org.apache.felix.scrplugin.mojo.MavenLog

object SCRDescriptorMojo {
  final val DEFAULT_FINAL_NAME = "scalascrComponents.xml"
  final val DEFAULT_METATYPE_NAME = "scalascrMetatype.xml"
  final val DEFAULT_OUTPUT_DIRECTORY = "${project.build.directory}/scr-plugin-generated"
  final val SCR_ANN_GROUPID = "org.apache.felix"
  final val SCR_ANN_ARTIFACTID = "org.apache.felix.scr.annotations"
  final val SCR_ANN_MIN_VERSION = new DefaultArtifactVersion("1.6.9")
}

/**
 *
 *
 * @since 1.0
 * @author Mark Adamcin
 */
@Mojo(name = "scr-descriptor",
  defaultPhase = LifecyclePhase.PROCESS_CLASSES,
  requiresDependencyResolution = ResolutionScope.COMPILE,
  threadSafe = true)
class SCRDescriptorMojo extends AbstractMojo {

  @Component
  var project: MavenProject = null

  @Parameter(required = true, defaultValue = SCRDescriptorMojo.DEFAULT_OUTPUT_DIRECTORY)
  val outputDirectory: File = null

  /**
   * Name of the generated descriptor.
   */
  @Parameter(defaultValue = SCRDescriptorMojo.DEFAULT_FINAL_NAME)
  val finalName = SCRDescriptorMojo.DEFAULT_FINAL_NAME

  /**
   * Name of the generated meta type file.
   */
  @Parameter(defaultValue = SCRDescriptorMojo.DEFAULT_METATYPE_NAME)
  val metaTypeName = SCRDescriptorMojo.DEFAULT_METATYPE_NAME

  /**
   * This flag controls the generation of the bind/unbind methods.
   */
  @Parameter(defaultValue = "true")
  val generateAccessors = true

  /**
   * In strict mode the plugin even fails on warnings.
   */
  @Parameter(defaultValue = "false")
  val strictMode = false

  /**
   * Predefined properties.
   */
  @Parameter
  val properties = java.util.Collections.emptyMap[String, String]

  /**
   * The comma separated list of tokens to exclude when processing sources.
   */
  @Parameter(alias = "excludes")
  val sourceExcludes: String = null

  /**
   * The comma separated list of tokens to include when processing sources.
   */
  @Parameter(alias = "includes")
  val sourceIncludes: String = null

  /**
   * The version of the DS spec this plugin generates a descriptor for.
   * By default the version is detected by the used tags.
   */
  @Parameter
  val specVersion: String = null

  /**
   * Project types which this plugin supports.
   */
  @Parameter
  val supportedProjectTypes: java.util.List[String] = List("jar", "bundle")

  @throws(classOf[MojoExecutionException])
  @throws(classOf[MojoFailureException])
  def execute() {
    val projectType = project.getArtifact.getType

    if (!(supportedProjectTypes contains projectType)) {
      getLog.debug("Ignoring project type " + projectType + " - supportedProjectTypes = " + supportedProjectTypes)
    } else {

      val scrLog = new MavenLog(getLog)


    }
  }

  def setServiceComponentHeader(files: List[String]) {
    Option(files) match {
      case Some(list) => {
        val svcHeader = project.getProperties.getProperty(ComponentConstants.SERVICE_COMPONENT)
        val xmlFiles = Option(svcHeader) match {
          case Some(header) => header.split(',').foldLeft(Set.empty[String]) { (files, token) => files + (token.trim) }
          case None => Set.empty[String]
        }

        val newHeader = (xmlFiles ++ files.toSet).mkString(", ")
        getLog.debug("...setting project property: " + ComponentConstants.SERVICE_COMPONENT + " = " + newHeader)
        project.getProperties.setProperty(ComponentConstants.SERVICE_COMPONENT, newHeader)
      }
      case _ => ()
    }
  }

  def updateProjectResources() {
    val ourRsrcPath =
  }
}