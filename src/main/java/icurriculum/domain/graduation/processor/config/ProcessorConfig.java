package icurriculum.domain.graduation.processor.config;

import icurriculum.domain.department.DepartmentName;
import icurriculum.domain.graduation.processor.*;
import icurriculum.domain.graduation.processor.strategy.generalrequirement.strategy.GeneralRequirementStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ProcessorConfig {

    @Bean
    public Processor<?, ?> coreProcessor() {
        return new CoreProcessor();
    }

    @Bean
    public Processor<?, ?> creativeProcessor() {
        return new CreativeProcessor();
    }

    @Bean
    public Processor<?, ?> swAiProcessor() {
        return new SwAiProcessor();
    }

    @Bean
    public Processor<?, ?> generalRequirementProcessor(Map<DepartmentName, GeneralRequirementStrategy> generalRequirementStrategyMap) {
        return new GeneralRequirementProcessor(generalRequirementStrategyMap);
    }

    @Bean
    public Map<ProcessorCategory, Processor<?, ?>> processorMap(List<Processor<?, ?>> processors) {
        Map<ProcessorCategory, Processor<?, ?>> processorMap = new EnumMap<>(ProcessorCategory.class);
        for (Processor<?, ?> processor : processors) {
            if (processor instanceof CreativeProcessor) {
                processorMap.put(ProcessorCategory.창의, processor);
                continue;
            }
            if (processor instanceof SwAiProcessor) {
                processorMap.put(ProcessorCategory.SW_AI, processor);
                continue;
            }
            if (processor instanceof CoreProcessor) {
                processorMap.put(ProcessorCategory.핵심교양, processor);
                continue;
            }
            if (processor instanceof GeneralRequirementProcessor) {
                processorMap.put(ProcessorCategory.교양필수, processor);
            }
        }
        return processorMap;
    }
}
