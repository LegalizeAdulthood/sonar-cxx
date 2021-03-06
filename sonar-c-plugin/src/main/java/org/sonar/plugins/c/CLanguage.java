/*
 * Sonar C++ Plugin (Community)
 * Copyright (C) 2010-2019 SonarOpenCommunity
 * http://github.com/SonarOpenCommunity/sonar-cxx
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.plugins.c;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.sonar.api.config.Configuration;
import org.sonar.cxx.CxxLanguage;

/**
 *
 * @author jocs
 */
public class CLanguage extends CxxLanguage {

  /**
   * c key
   */
  public static final String KEY = "c";

  /**
   * c name
   */
  public static final String NAME = "C (Community)";

  /**
   * Default c source files suffixes
   */
  public static final String DEFAULT_SOURCE_SUFFIXES = ".c";
  public static final String DEFAULT_C_FILES = "*.c,*.C";

  /**
   * Default c header files suffixes
   */
  public static final String DEFAULT_HEADER_SUFFIXES = ".h";

  /**
   * c analysis parameters key
   */
  public static final String PROPSKEY = "c";

  /**
   * c repository key
   */
  public static final String REPOSITORY_KEY = "c";
  public static final String DEFAULT_PROFILE = "Sonar way";

  private final String[] sourceSuffixes;
  private final String[] headerSuffixes;
  private final String[] fileSuffixes;

  public CLanguage(Configuration settings) {
    super(KEY, NAME, PROPSKEY, settings);

    sourceSuffixes
      = createStringArray(settings.getStringArray(CPlugin.SOURCE_FILE_SUFFIXES_KEY), DEFAULT_SOURCE_SUFFIXES);
    headerSuffixes
      = createStringArray(settings.getStringArray(CPlugin.HEADER_FILE_SUFFIXES_KEY), DEFAULT_HEADER_SUFFIXES);
    fileSuffixes = mergeArrays(sourceSuffixes, headerSuffixes);
  }

  private static String[] createStringArray(String[] values, String defaultValues) {
    if (values.length == 0) {
      return defaultValues.split(",");
    }
    return values;
  }

  @Override
  public String[] getFileSuffixes() {
    return fileSuffixes.clone();
  }

  @Override
  public String[] getSourceFileSuffixes() {
    return sourceSuffixes.clone();
  }

  @Override
  public String getRepositorySuffix() {
    return "-c";
  }

  @Override
  public String[] getHeaderFileSuffixes() {
    return headerSuffixes.clone();
  }

  @Override
  public List<Class> getChecks() {
    return new ArrayList<>(Arrays.asList(
      org.sonar.cxx.checks.api.UndocumentedApiCheck.class,
      org.sonar.cxx.checks.error.MissingIncludeFileCheck.class,
      org.sonar.cxx.checks.error.ParsingErrorCheck.class,
      org.sonar.cxx.checks.error.ParsingErrorRecoveryCheck.class,
      org.sonar.cxx.checks.file.FileEncodingCheck.class,
      org.sonar.cxx.checks.file.MissingNewLineAtEndOfFileCheck.class,
      org.sonar.cxx.checks.file.TabCharacterCheck.class,
      org.sonar.cxx.checks.metrics.ClassComplexityCheck.class,
      org.sonar.cxx.checks.metrics.FileComplexityCheck.class,
      org.sonar.cxx.checks.metrics.FunctionCognitiveComplexityCheck.class,
      org.sonar.cxx.checks.metrics.FunctionComplexityCheck.class,
      org.sonar.cxx.checks.metrics.TooLongLineCheck.class,
      org.sonar.cxx.checks.metrics.TooManyLinesOfCodeInFileCheck.class,
      org.sonar.cxx.checks.metrics.TooManyLinesOfCodeInFunctionCheck.class,
      org.sonar.cxx.checks.metrics.TooManyParametersCheck.class,
      org.sonar.cxx.checks.metrics.TooManyStatementsPerLineCheck.class,
      org.sonar.cxx.checks.naming.ClassNameCheck.class,
      org.sonar.cxx.checks.naming.FileNameCheck.class,
      org.sonar.cxx.checks.naming.FunctionNameCheck.class,
      org.sonar.cxx.checks.naming.MethodNameCheck.class,
      org.sonar.cxx.checks.regex.CommentRegularExpressionCheck.class,
      org.sonar.cxx.checks.regex.FileHeaderCheck.class,
      org.sonar.cxx.checks.regex.FileRegularExpressionCheck.class,
      org.sonar.cxx.checks.regex.FixmeTagPresenceCheck.class,
      org.sonar.cxx.checks.regex.LineRegularExpressionCheck.class,
      org.sonar.cxx.checks.regex.NoSonarCheck.class,
      org.sonar.cxx.checks.regex.TodoTagPresenceCheck.class,
      org.sonar.cxx.checks.xpath.XPathCheck.class
    ));
  }

  @Override
  public String getRepositoryKey() {
    return REPOSITORY_KEY;
  }

  private String[] mergeArrays(String[] array1, String[] array2) {
    String[] result = new String[array1.length + array2.length];
    System.arraycopy(sourceSuffixes, 0, result, 0, array1.length);
    System.arraycopy(headerSuffixes, 0, result, array1.length, array2.length);
    return result;
  }

}
