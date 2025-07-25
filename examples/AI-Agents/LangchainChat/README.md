
// import dev.langchain4j.model.chat.ChatModel;
// import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// @Configuration
// public class LangchainBean {

//     // Define a bean named "chatModel"
//     @Bean("chatModel")
//     public ChatModel chatModel() {
//         return GoogleAiGeminiChatModel.builder()
//             .apiKey("AIzaSyARf1E3zznDMvDVxUDnO2NquhbqQfeRTHc")
//             .modelName("gemini-2.5-flash")
//             .temperature(0.7)
//             .maxOutputTokens(1000)
//             .build();
//     }
// }