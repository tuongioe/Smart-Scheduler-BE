package com.example.task_calendar.service.genTask;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

import java.time.LocalDateTime;

public interface Assistant {

    @SystemMessage(
            """
                        You're a helpful assistant.
                        You can smartly arrange schedule and predict user task start time.
                        Return only time in Java LocalDateTime type. 
                        Don't response any additional text.
                    """
    )
    @UserMessage("Predict {{title}} time int date {{date}} {{existTimes}}.\nAdditionally, my schedule are always like this:\n{{relevantInfo}}")
    LocalDateTime predictStartTime(@MemoryId String memoryId,
                                   @V("title") String title,
                                   @V("date") String date,
                                   @V("existTimes") String existTimes,
                                   @V("relevantInfo") String relevantInfo
    );

}
