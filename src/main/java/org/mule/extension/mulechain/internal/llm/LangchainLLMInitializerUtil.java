package org.mule.extension.mulechain.internal.llm;

import dev.langchain4j.model.anthropic.AnthropicChatModel;
import dev.langchain4j.model.azure.AzureOpenAiChatModel;
import dev.langchain4j.model.mistralai.MistralAiChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.mule.extension.mulechain.internal.llm.config.ConfigExtractor;

import static java.time.Duration.ofSeconds;

public final class LangchainLLMInitializerUtil {

  private LangchainLLMInitializerUtil() {}

  public static OpenAiChatModel createOpenAiChatModel(ConfigExtractor configExtractor, LangchainLLMConfiguration configuration) {
    String openaiApiKey = configExtractor.extractValue("OPENAI_API_KEY");
    return OpenAiChatModel.builder()
        .apiKey(openaiApiKey)
        .modelName(configuration.getModelName())
        .maxTokens(configuration.getMaxTokens())
        .temperature(configuration.getTemperature())
        .timeout(ofSeconds(configuration.getDurationInSeconds()))
        .logRequests(true)
        .logResponses(true)
        .build();

  }

  public static OpenAiChatModel createGroqOpenAiChatModel(ConfigExtractor configExtractor,
                                                          LangchainLLMConfiguration configuration) {
    String groqApiKey = configExtractor.extractValue("GROQ_API_KEY");
    return OpenAiChatModel.builder()
        .baseUrl("https://api.groq.com/openai/v1")
        .apiKey(groqApiKey)
        .modelName(configuration.getModelName())
        .maxTokens(configuration.getMaxTokens())
        .temperature(configuration.getTemperature())
        .timeout(ofSeconds(configuration.getDurationInSeconds()))
        .logRequests(true)
        .logResponses(true)
        .build();

  }


  public static MistralAiChatModel createMistralAiChatModel(ConfigExtractor configExtractor,
                                                            LangchainLLMConfiguration configuration) {
    String mistralAiApiKey = configExtractor.extractValue("MISTRAL_AI_API_KEY");
    return MistralAiChatModel.builder()
        //.apiKey(configuration.getLlmApiKey())
        .apiKey(mistralAiApiKey)
        .modelName(configuration.getModelName())
        .maxTokens(configuration.getMaxTokens())
        .temperature(configuration.getTemperature())
        .timeout(ofSeconds(configuration.getDurationInSeconds()))
        .logRequests(true)
        .logResponses(true)
        .build();
  }

  public static OllamaChatModel createOllamaChatModel(ConfigExtractor configExtractor, LangchainLLMConfiguration configuration) {
    String ollamaBaseUrl = configExtractor.extractValue("OLLAMA_BASE_URL");
    return OllamaChatModel.builder()
        //.baseUrl(configuration.getLlmApiKey())
        .baseUrl(ollamaBaseUrl)
        .modelName(configuration.getModelName())
        .temperature(configuration.getTemperature())
        .timeout(ofSeconds(configuration.getDurationInSeconds()))
        .build();
  }


  public static AnthropicChatModel createAnthropicChatModel(ConfigExtractor configExtractor,
                                                            LangchainLLMConfiguration configuration) {
    String anthropicApiKey = configExtractor.extractValue("ANTHROPIC_API_KEY");
    return AnthropicChatModel.builder()
        //.apiKey(configuration.getLlmApiKey())
        .apiKey(anthropicApiKey)
        .modelName(configuration.getModelName())
        .maxTokens(configuration.getMaxTokens())
        .temperature(configuration.getTemperature())
        .timeout(ofSeconds(configuration.getDurationInSeconds()))
        .logRequests(true)
        .logResponses(true)
        .build();
  }


  public static AzureOpenAiChatModel createAzureOpenAiChatModel(ConfigExtractor configExtractor,
                                                                LangchainLLMConfiguration configuration) {
    String azureOpenaiKey = configExtractor.extractValue("AZURE_OPENAI_KEY");
    String azureOpenaiEndpoint = configExtractor.extractValue("AZURE_OPENAI_ENDPOINT");
    String azureOpenaiDeploymentName = configExtractor.extractValue("AZURE_OPENAI_DEPLOYMENT_NAME");
    return AzureOpenAiChatModel.builder()
        .apiKey(azureOpenaiKey)
        .endpoint(azureOpenaiEndpoint)
        .deploymentName(azureOpenaiDeploymentName)
        .maxTokens(configuration.getMaxTokens())
        .temperature(configuration.getTemperature())
        .timeout(ofSeconds(configuration.getDurationInSeconds()))
        .logRequestsAndResponses(true)
        .build();
  }
}
