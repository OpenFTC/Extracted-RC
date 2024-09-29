/*
 * Copyright 2016 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.blocks.ftcrobotcontroller.util;

import com.google.blocks.ftcrobotcontroller.hardware.HardwareType;

import java.util.Map;

/**
 * A class that provides utility methods related to the toolbox.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
public class ToolboxUtil {
  // Prevent instantiation of util class.
  private ToolboxUtil() {
  }

  /**
   * Creates a shadow number block with the given number.
   */
  public static String makeNumberShadow(int n) {
    return "<shadow type=\"math_number\"><field name=\"NUM\">" + n + "</field></shadow>\n";
  }

  /**
   * Creates a shadow number block with the given number.
   */
  public static String makeNumberShadow(double n) {
    return "<shadow type=\"math_number\"><field name=\"NUM\">" + n + "</field></shadow>\n";
  }

  /**
   * Creates a shadow boolean block with the given value.
   */
  public static String makeBooleanShadow(boolean value) {
    String b = value ? "TRUE" : "FALSE";
    return "<shadow type=\"logic_boolean\"><field name=\"BOOL\">" + b + "</field></shadow>\n";
  }

  /**
   * Creates a shadow text block with the given value.
   */
  public static String makeTextShadow(String text) {
    return "<shadow type=\"text\"><field name=\"TEXT\">" + escapeForXml(text) + "</field></shadow>\n";
  }

  /**
   * Creates an enum block with the given {@link HardwareType} and enum type.
   */
  public static String makeTypedEnumBlock(HardwareType hardwareType, String enumType) {
    return "<block type=\"" + hardwareType.blockTypePrefix + "_typedEnum_" + enumType + "\">\n"
        + "</block>\n";
  }

  /**
   * Creates an enum block with the given {@link HardwareType}, enum type, fieldName, and
   * fieldValue.
   */
  static String makeTypedEnumBlock(HardwareType hardwareType, String enumType,
      String fieldName, String fieldValue) {
    return "<block type=\"" + hardwareType.blockTypePrefix + "_typedEnum_" + enumType + "\">\n"
        + "<field name=\"" + fieldName + "\">" + fieldValue + "</field>"
        + "</block>\n";
  }

  /**
   * Creates a shadow enum block with the given {@link HardwareType} and enum type.
   */
  public static String makeTypedEnumShadow(HardwareType hardwareType, String enumType) {
    return makeTypedEnumShadow(hardwareType.blockTypePrefix, enumType);
  }

  /**
   * Creates a shadow enum block with the given {@link HardwareType}, enum type, fieldName, and
   * fieldValue.
   */
  public static String makeTypedEnumShadow(HardwareType hardwareType, String enumType,
      String fieldName, String fieldValue) {
    return makeTypedEnumShadow(hardwareType.blockTypePrefix, enumType, fieldName, fieldValue);
  }

  /**
   * Creates a shadow enum block with the given blockTypePrefix and enum type.
   */
  public static String makeTypedEnumShadow(String blockTypePrefix, String enumType) {
    return "<shadow type=\"" + blockTypePrefix + "_typedEnum_" + enumType + "\">\n"
        + "</shadow>\n";
  }

  /**
   * Creates a shadow enum block with the given blockTypePrefix, enum type, fieldName, and fieldValue.
   */
  public static String makeTypedEnumShadow(String blockTypePrefix, String enumType,
      String fieldName, String fieldValue) {
    return "<shadow type=\"" + blockTypePrefix + "_typedEnum_" + enumType + "\">\n"
        + "<field name=\"" + fieldName + "\">" + fieldValue + "</field>"
        + "</shadow>\n";
  }

  /**
   * Creates a variable get block.
   */
  public static String makeVariableGetBlock(String t) {
    return "<block type=\"variables_get\"><field name=\"VAR\">" + t + "</field></block>\n";
  }

  /**
   * Appends a property setter block to the toolbox.
   */
  private static void addPropertySetter(
      StringBuilder xmlToolbox, HardwareType hardwareType, String identifier,
      String propertyName, String propertyType, String setterValue) {
    xmlToolbox
        .append("<block type=\"").append(hardwareType.blockTypePrefix).append("_setProperty_").append(propertyType).append("\">\n")
        .append("<field name=\"IDENTIFIER\">").append(identifier).append("</field>\n")
        .append("<field name=\"PROP\">").append(propertyName).append("</field>\n")
        .append("<value name=\"VALUE\">\n").append(setterValue).append("</value>\n")
        .append("</block>\n");
  }

  /**
   * Appends dual property setter blocks to the toolbox.
   */
  public static void addDualPropertySetters(
      StringBuilder xmlToolbox, HardwareType hardwareType, String propertyName, String propertyType,
      String identifier1, String setterValue1,
      String identifier2, String setterValue2) {
    xmlToolbox
        .append("<block type=\"").append(hardwareType.blockTypePrefix).append("_setDualProperty_").append(propertyType).append("\">\n")
        .append("<field name=\"PROP\">").append(propertyName).append("</field>\n")
        .append("<field name=\"IDENTIFIER1\">").append(identifier1).append("</field>\n")
        .append("<field name=\"IDENTIFIER2\">").append(identifier2).append("</field>\n")
        .append("<value name=\"VALUE1\">\n").append(setterValue1).append("</value>\n")
        .append("<value name=\"VALUE2\">\n").append(setterValue2).append("</value>\n")
        .append("</block>\n");
  }

  /**
   * Appends a property getter block to the toolbox.
   */
  private static void addPropertyGetter(
      StringBuilder xmlToolbox, HardwareType hardwareType, String identifier,
      String propertyName, String propertyType) {
    xmlToolbox
        .append("<block type=\"").append(hardwareType.blockTypePrefix).append("_getProperty_").append(propertyType).append("\">\n")
        .append("<field name=\"IDENTIFIER\">").append(identifier).append("</field>\n")
        .append("<field name=\"PROP\">").append(propertyName).append("</field>\n")
        .append("</block>\n");
  }

  /**
   * Appends the property blocks for the given {@link HardwareType} to the toolbox.
   */
  public static void addProperties(
      StringBuilder xmlToolbox, HardwareType hardwareType, String identifier,
      Map<String, String> properties, Map<String, String[]> setterValues,
      Map<String, String> enumBlocks) {

    for (Map.Entry<String, String> property : properties.entrySet()) {
      String propertyName = property.getKey();
      String propertyType = property.getValue();
      if (setterValues != null && setterValues.containsKey(propertyName)) {
        for (String setterValue : setterValues.get(propertyName)) {
          addPropertySetter(
              xmlToolbox, hardwareType, identifier, propertyName, propertyType, setterValue);
        }
      }
      if (enumBlocks != null && enumBlocks.containsKey(propertyName)) {
        // For an enum property, provide a logic_compare block that compares the property getter with the enum.
        xmlToolbox
            .append("<block type=\"logic_compare\"><field name=\"OP\">EQ</field><value name=\"A\">");
        addPropertyGetter(xmlToolbox, hardwareType, identifier, propertyName, propertyType);
        xmlToolbox
            .append("</value><value name=\"B\">")
            .append(enumBlocks.get(propertyName))
            .append("</value></block>");
      } else {
        // For other properties, just provide the property getter block.
        addPropertyGetter(xmlToolbox, hardwareType, identifier, propertyName, propertyType);
      }
    }
  }

  /**
   * Appends the function blocks for the given {@link HardwareType} to the toolbox.
   */
  public static void addFunctions(
      StringBuilder xmlToolbox, HardwareType hardwareType, String identifier,
      Map<String, Map<String, String>> functions) {
    addFunctions(xmlToolbox, hardwareType, identifier, functions,
                 null /* functionComments */, null /* variableSetters */, null /* enumBlocks */);
  }

  /**
   * Appends the function blocks for the given {@link HardwareType} to the toolbox.
   */
  public static void addFunctions(
      StringBuilder xmlToolbox, HardwareType hardwareType, String identifier,
      Map<String, Map<String, String>> functions,
      Map<String, String> functionComments, Map<String, String> variableSetters,
      Map<String, String> enumBlocks) {
    for (Map.Entry<String, Map<String, String>> functionEntry : functions.entrySet()) {
      String functionName = functionEntry.getKey();
      Map<String, String> args = functionEntry.getValue();

      String enumBlock = (enumBlocks != null) ? enumBlocks.get(functionName) :  null;
      String variableSetter = (variableSetters != null) ? variableSetters.get(functionName) : null;

      if (enumBlock != null) {
        // For a function that returns enum, provide a logic_compare block that compares the function with the enum.
        xmlToolbox
            .append("<block type=\"logic_compare\"><field name=\"OP\">EQ</field><value name=\"A\">");
      } else if (variableSetter != null) {
        xmlToolbox
            .append("<block type=\"variables_set\">")
            .append("<field name=\"VAR\">").append(variableSetter).append("</field>")
            .append("<value name=\"VALUE\">\n");
      }

      xmlToolbox
          .append("<block type=\"").append(hardwareType.blockTypePrefix).append("_")
          .append(functionName).append("\">\n");

      String comment = (functionComments != null) ? functionComments.get(functionName) : null;
      if (comment != null && comment.startsWith("<comment") && comment.endsWith("</comment>")) {
        xmlToolbox
            .append(comment).append("\n");
      }

      xmlToolbox
          .append("<field name=\"IDENTIFIER\">").append(identifier)
          .append("</field>\n");
      if (args != null) {
        for (Map.Entry<String, String> argEntry : args.entrySet()) {
          String argName = argEntry.getKey();
          String value = argEntry.getValue();
          xmlToolbox
              .append("<value name=\"" + argName + "\">\n")
              .append(value)
              .append("</value>\n");
        }
      }
      xmlToolbox
          .append("</block>\n");

      if (enumBlock != null) {
        xmlToolbox
            .append("</value><value name=\"B\">")
            .append(enumBlock)
            .append("</value></block>");
      } else if (variableSetter != null) {
        xmlToolbox
            .append("</value></block>");
      }
    }
  }

  public static String escapeForXml(String s) {
    return s
        .replace("&", "&amp;")
        .replace("\n", "&#x0D;")
        .replace("\"", "&quot;")
        .replace("'", "&apos;")
        .replace("<", "&lt;")
        .replace(">", "&gt;");
  }
}
