package icurriculum.global.util;

import icurriculum.domain.graduation.processor.Processor;
import icurriculum.domain.graduation.processor.config.ProcessorCategory;
import java.util.Map;

public abstract class ProcessorUtils {

    public static <T, R> Processor<T, R> get(
        Map<ProcessorCategory, Processor<?, ?>> processorMap,
        ProcessorCategory category
    ) {
        Processor<?, ?> processor = processorMap.get(category);
        if (processor == null) {
            /**
             * Todo 예외 추후 정의
             */
            throw new RuntimeException();
        }
        return (Processor<T, R>) processor;
    }
}