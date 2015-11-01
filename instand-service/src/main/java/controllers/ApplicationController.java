/**
 * Copyright (C) 2013 the original author or authors.
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

package controllers;

import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.Maps;
import ninja.BasicAuthFilter;
import ninja.FilterWith;
import ninja.Result;
import ninja.Results;

import com.google.inject.Singleton;
import ninja.utils.NinjaConstant;
import ninja.utils.NinjaProperties;

import javax.inject.Inject;


@Singleton
public class ApplicationController {

    @Inject
    NinjaProperties ninjaProperties;

    public Result index() {
        return Results.html();
    }

    public Result scaffold() {
        return Results.html();
    }

    @FilterWith(BasicAuthFilter.class)
    public Result config() {
        Result result = Results.html();

        // all ninja properties
        result.render("ninaProperties", ImmutableSortedMap.copyOf(Maps.fromProperties(ninjaProperties.getAllCurrentNinjaProperties())));

        // only framework active properties
        result.render("mode", System.getProperties().getProperty(NinjaConstant.MODE_KEY_NAME));
        result.render("applicationName", ninjaProperties.get(NinjaConstant.applicationName));

        // only our active properties
        result.render("stage", ninjaProperties.get("stage"));

        return result;
    }

    public Result helloWorldJson() {
        SimplePojo simplePojo = new SimplePojo();
        simplePojo.content = "JSON is working!";
        return Results.json().render(simplePojo);
    }
    
    public static class SimplePojo {
        public String content;
    }
}
