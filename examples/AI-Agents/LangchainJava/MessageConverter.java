// package com.yourpackage; // Optional: add your package declaration if you use one

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.UserMessage;
import org.apache.camel.Handler; // Import the Camel Handler annotation
import org.springframework.stereotype.Component; // Import Spring Component annotation

// Use @Component to register this class as a bean in the Camel registry.
// Camel JBang will discover this when Spring integration is enabled (which is common).
@Component
public class MessageConverter {
    
    /**
     * Camel will automatically route the incoming message body (if it's a String)
     * to the 'body' parameter of this method.
     * The return value of this method will become the new message body.
     * @param body The incoming message body as a String.
     * @return A UserMessage object containing the input text.
     */
    @Handler // Marks this method as the one Camel should invoke
    public ChatMessage toUserMessage(String body) {
        // Create a LangChain4j UserMessage from the input string
        return UserMessage.userMessage(body);
    }

    // If you needed to handle other message types or more complex logic,
    // you could add more methods here, potentially with different parameters,
    // or use Camel's TypeConverter system.
}