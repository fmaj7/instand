/**
 * Copyright (C) 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package conf;


import controllers.InterpretationController;
import controllers.SubjectController;
import controllers.UserController;
import ninja.AssetsController;
import ninja.Router;
import ninja.application.ApplicationRoutes;
import controllers.ApplicationController;

public class Routes implements ApplicationRoutes {

    @Override
    public void init(Router router) {  

        // Main
        router.GET().route("/").with(ApplicationController.class, "index");
        router.GET().route("/hello-world.json").with(ApplicationController.class, "helloWorldJson");

        // Users
        router.POST().route("/users").with(UserController.class, "create");
        router.GET().route("/users/{id}").with(UserController.class, "get");
        router.GET().route("/users/q/username/{username}").with(UserController.class, "findByUsername");
        router.GET().route("/users/q/email-address/{emailAddress}").with(UserController.class, "findByEmailAddress");
        router.GET().route("/users").with(UserController.class, "findAll");

        // Subjects
        router.POST().route("/subjects").with(SubjectController.class, "create");
        router.GET().route("/subjects/{id}").with(SubjectController.class, "get");
        router.GET().route("/subjects").with(SubjectController.class, "findAll");

        // Interpretations
        router.POST().route("/interpretations").with(InterpretationController.class, "create");
        router.GET().route("/interpretations/{id}").with(InterpretationController.class, "get");
        router.GET().route("/interpretations").with(InterpretationController.class, "findAll");
        router.GET().route("/interpretations/q/subject/{subjectId}").with(InterpretationController.class, "findByInterpretingSubjectId");

        ///////////////////////////////////////////////////////////////////////
        // Assets (pictures / javascript)
        ///////////////////////////////////////////////////////////////////////    
        router.GET().route("/assets/webjars/{fileName: .*}").with(AssetsController.class, "serveWebJars");
        router.GET().route("/assets/{fileName: .*}").with(AssetsController.class, "serveStatic");
        
        ///////////////////////////////////////////////////////////////////////
        // Index / Catchall shows index page
        ///////////////////////////////////////////////////////////////////////
        router.GET().route("/.*").with(ApplicationController.class, "index");
    }

}
