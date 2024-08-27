package icurriculum.domain.graduation.processor.util;

import icurriculum.domain.graduation.processor.ProcessorCategory;
import icurriculum.domain.graduation.processor.Processor;

import java.util.Map;

public class ProcessorUtils {
    public static <T, R> Processor<T, R> getProcessorByCategory(Map<ProcessorCategory, Processor<?, ?>> processorMap, ProcessorCategory category) {
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
