/*
 * Copyright 2016 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version
 * 2.0 (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */
package io.jenkins.updatebot.commands;

import io.jenkins.updatebot.Configuration;
import io.jenkins.updatebot.repository.LocalRepository;
import io.jenkins.updatebot.support.Markdown;
import io.jenkins.updatebot.support.Strings;

/**
 */
public class PushRegexChangesContext extends CommandContext {
    private PushRegexChanges command;

/*
    public PushRegexChangesContext(CommandContext parentContext, DependencyVersionChange step) {
        super(parentContext);
        this.step = step;
    }
*/

    public PushRegexChangesContext(LocalRepository repository, Configuration configuration, PushRegexChanges command) {
        super(repository, configuration);
        this.command = command;
    }

    @Override
    public String toString() {
        return "PushRegexChangesContext{" +
                "regex='" + command.getRegex() + '\'' +
                ", value='" + command.getValue() + '\'' +
                '}';
    }

    @Override
    public String createPullRequestBody() {
        String body = Markdown.UPDATEBOT_ICON + " pushed regex: `" + command.getRegex() + "` to: `" + command.getValue() + "`";
        if (Strings.notEmpty(command.getComponentName())) {
            body += " for " + command.getComponentName();
        }
        return body;
    }

    @Override
    public String createCommit() {
        return "fix(regex): update " + componentNameOrRegex() + " to " + command.getValue();
    }

    @Override
    public String createPullRequestTitle() {
        return createPullRequestTitlePrefix() + command.getValue();
    }


    @Override
    public String createPullRequestTitlePrefix() {
        return "update " + componentNameOrRegex() + " to ";
    }

    private String componentNameOrRegex() {
        if (Strings.notEmpty(command.getComponentName())) {
            return command.getComponentName();
        }
        return command.getRegex().toString();
    }
}
