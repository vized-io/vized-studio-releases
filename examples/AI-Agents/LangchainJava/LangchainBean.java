// dev.langchain4j:langchain4j:0.36.0, 
//DEPS dev.langchain4j:langchain4j-ollama:0.36.0

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// @Configuration is a Spring annotation that Camel JBang understands
// It tells Camel to scan this class for @Bean methods.
@Configuration
public class LangchainBean {

    // Define a bean named "chatModel"
    @Bean("chatModel")
    public ChatLanguageModel chatModel() {
        // Use the builder pattern to create the OllamaChatModel instance
        // Since this is a standard Java file compiled by JBang's Java loader,
        // imports work normally here.
        return OllamaChatModel.builder()
            .baseUrl("http://192.168.31.142:11434/") // Set properties on the builder
            .modelName("qwen3:0.6b")              // Note: LangChain4j builders often use modelName, not model
            .temperature(0.7)                     // Set properties on the builder
            // Add other builder methods if needed (e.g., timeout, logRequests)
            .build();                             // Build the final instance
    }
}