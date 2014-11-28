/*
 * Copyright 2014 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.grails.cli.gradle

import groovy.transform.CompileStatic
import org.gradle.tooling.BuildLauncher
import org.grails.cli.profile.ExecutionContext


/**
 * Allow dynamic invocation of Gradle tasks
 *
 * @author Graeme Rocher
 * @since 3.0
 */
@CompileStatic
class GradleInvoker {

    ExecutionContext executionContext

    GradleInvoker(ExecutionContext executionContext) {
        this.executionContext = executionContext
    }

    @Override
    Object invokeMethod(String name, Object args) {
        Object[] argArray = (Object[]) args


        GradleUtil.runBuildWithConsoleOutput(executionContext) { BuildLauncher buildLauncher ->
            buildLauncher.forTasks(name.split(' '))
            buildLauncher.withArguments(argArray.collect() { it.toString() } as String[])
        }
    }
}
