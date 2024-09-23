package org.mule.extension.mulechain.internal.llm.config;

import org.json.JSONObject;
import org.mule.extension.mulechain.internal.config.LangchainLLMConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.mule.extension.mulechain.internal.util.JsonUtils.readConfigFile;

public class FileConfigExtractor implements ConfigExtractor {

  private static final Logger LOGGER = LoggerFactory.getLogger(FileConfigExtractor.class);

  private JSONObject llmConfig;
  private JSONObject globalConfig;

  public FileConfigExtractor(LangchainLLMConfiguration configuration) {
    JSONObject config = readConfigFile(configuration.getFilePath());

    if (config != null) {
      LOGGER.debug("Configuration file successfully loaded from '{}'", configuration.getFilePath());

      // Get the LLM type section of the configuration if exists
      llmConfig = config.optJSONObject(configuration.getLlmType());

      // Capture the whole config as global in case we need global settings
      globalConfig = config;

      if (llmConfig == null) {
        LOGGER.warn("LLM type '{}' not found in configuration file.", configuration.getLlmType());
      } else {
        LOGGER.debug("LLM type '{}' section successfully loaded.", configuration.getLlmType());
      }
    } else {
      LOGGER.error("Failed to load configuration file from '{}'", configuration.getFilePath());
    }
  }

  @Override
  public String extractValue(String key) {
    // First try to extract from the LLM type section
    if (llmConfig != null && llmConfig.has(key)) {
      LOGGER.debug("Extracting key '{}' from LLM configuration section.", key);
      return llmConfig.optString(key);
    }

    // If not found, try to extract from the global config
    if (globalConfig != null && globalConfig.has(key)) {
      LOGGER.debug(
                   "Key '{}' not found in the LLM-specific section, extracting from the global configuration as a fallback.",
                   key);
      return globalConfig.optString(key);
    }

    LOGGER.warn("Key '{}' not found in either LLM configuration section or global configuration.", key);
    return null; // Return null if key not found
  }
}
