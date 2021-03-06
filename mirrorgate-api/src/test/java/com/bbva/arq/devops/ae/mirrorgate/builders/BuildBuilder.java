/*
 * Copyright 2017 Banco Bilbao Vizcaya Argentaria, S.A..
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
package com.bbva.arq.devops.ae.mirrorgate.builders;

import com.bbva.arq.devops.ae.mirrorgate.model.Build;
import com.bbva.arq.devops.ae.mirrorgate.support.BuildStatus;
import org.bson.types.ObjectId;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BuildBuilder {
    private BuildBuilder() {}

    public static Build makeBuild() {
        return makeBuild("http://repo1.com/");
    }

    public static Build makeBuild(String repoName, String branchName) {
        return makeBuild(repoName)
                .setBranch(branchName);
    }

    public static Build makeBuild(String repoName,
            String branchName, List<String> teamMembers) {
        return makeBuild(repoName, branchName)
                .setCulprits(teamMembers);
    }

    public static Build makeBuild(String repoName) {
        return new Build()
            .setTimestamp(System.currentTimeMillis())
            .setNumber("1")
            .setBuildUrl(ObjectId.get().toString())
            .setStartTime(3)
            .setEndTime(8)
            .setDuration(5)
            .setBuildStatus(BuildStatus.Success)
            .setCulprits(Collections.singletonList("foo"))
            .setProjectName("mirrorgate")
            .setRepoName(repoName)
            .setBranch("test")
            .setKeywords(Arrays.asList("buildUrl", "mirrorgate", repoName));
    }

}
