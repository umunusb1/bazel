// Copyright 2018 The Bazel Authors. All rights reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package com.google.devtools.build.lib.rules.android;

import com.google.common.collect.ImmutableList;
import com.google.devtools.build.lib.actions.Artifact;
import com.google.devtools.build.lib.concurrent.ThreadSafety.Immutable;
import com.google.devtools.build.lib.packages.BuiltinProvider;
import com.google.devtools.build.lib.packages.NativeInfo;
import com.google.devtools.build.lib.rules.java.ProguardLibrary;
import com.google.devtools.build.lib.skyframe.serialization.autocodec.AutoCodec;
import com.google.devtools.build.lib.skylarkbuildapi.android.AndroidProguardInfoApi;

/**
 * A target that can provide local proguard specifications, returned by the {@link
 * ProguardLibrary#collectLocalProguardSpecs()} method.
 *
 * <p>This class provides additional data, not available in the pure native {@link
 * com.google.devtools.build.lib.rules.java.ProguardSpecProvider} provider.
 */
@AutoCodec
@Immutable
public class AndroidProguardInfo extends NativeInfo implements AndroidProguardInfoApi<Artifact> {
  public static final Provider PROVIDER = new Provider();

  private final ImmutableList<Artifact> localProguardSpecs;

  public AndroidProguardInfo(ImmutableList<Artifact> localProguardSpecs) {
    super(PROVIDER);
    this.localProguardSpecs = localProguardSpecs;
  }

  @Override
  public ImmutableList<Artifact> getLocalProguardSpecs() {
    return localProguardSpecs;
  }

  /**
   * Provider class for {@link AndroidProguardInfo} objects.
   */
  public static class Provider extends BuiltinProvider<AndroidProguardInfo> {
    private Provider() {
      super(PROVIDER_NAME, AndroidProguardInfo.class);
    }
  }

  @Override
  public AndroidProguardInfo androidProguardInfo(ImmutableList<Artifact> localProguardSpecs) {
    return new AndroidProguardInfo(localProguardSpecs);
  }
}
