/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.beam.runners.spark.structuredstreaming.utils;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.apache.beam.runners.spark.structuredstreaming.translation.helpers.EncoderHelpers;
import org.apache.beam.sdk.coders.VarIntCoder;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/** Test of the wrapping of Beam Coders as Spark ExpressionEncoders. */
@RunWith(JUnit4.class)
public class EncodersTest {

  @Test
  public void beamCoderToSparkEncoderTest() {
    SparkSession sparkSession =
        SparkSession.builder()
            .appName("beamCoderToSparkEncoderTest")
            .master("local[4]")
            .getOrCreate();
    List<Integer> data = new ArrayList<>();
    data.add(1);
    data.add(2);
    data.add(3);
    Dataset<Integer> dataset =
        sparkSession.createDataset(data, EncoderHelpers.fromBeamCoder(VarIntCoder.of()));
    List<Integer> results = dataset.collectAsList();
    assertEquals(data, results);
  }
}
