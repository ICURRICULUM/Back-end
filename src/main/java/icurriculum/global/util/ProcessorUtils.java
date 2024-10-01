package icurriculum.global.util;

import icurriculum.domain.graduation.service.module.processor.Processor;
import icurriculum.domain.graduation.service.module.processor.config.ProcessorCategory;
import icurriculum.global.response.exception.GeneralException;
import icurriculum.global.response.status.ErrorStatus;
import java.util.Map;

public abstract class ProcessorUtils {

    @SuppressWarnings("unchecked")
    public static <T, R> Processor<T, R> get(
        Map<ProcessorCategory, Processor<?, ?>> processorMap,
        ProcessorCategory category
    ) {
        Processor<?, ?> processor = processorMap.get(category);
        if (processor == null) {
            throw new GeneralException(ErrorStatus.PROCESSOR_FIND_EXCEPTION);
        }

        return (Processor<T, R>) processor;
    }
}