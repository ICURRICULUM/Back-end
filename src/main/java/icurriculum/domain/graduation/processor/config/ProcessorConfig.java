package icurriculum.domain.graduation.processor.config;

import icurriculum.domain.department.DepartmentName;
import icurriculum.domain.graduation.processor.Processor;
import icurriculum.domain.graduation.processor.core.CoreProcessor;
import icurriculum.domain.graduation.processor.core.strategy.CoreStrategy;
import icurriculum.domain.graduation.processor.creativity.CreativityProcessor;
import icurriculum.domain.graduation.processor.creativity.strategy.CreativityStrategy;
import icurriculum.domain.graduation.processor.generalrequired.GeneralRequiredProcessor;
import icurriculum.domain.graduation.processor.generalrequired.strategy.GeneralRequiredStrategy;
import icurriculum.domain.graduation.processor.generalselect.GeneralSelectProcessor;
import icurriculum.domain.graduation.processor.generalselect.strategy.GeneralSelectStrategy;
import icurriculum.domain.graduation.processor.majorrequired.MajorRequiredProcessor;
import icurriculum.domain.graduation.processor.majorrequired.strategy.MajorRequiredStrategy;
import icurriculum.domain.graduation.processor.majorselect.MajorSelectProcessor;
import icurriculum.domain.graduation.processor.majorselect.strategy.MajorSelectStrategy;
import icurriculum.domain.graduation.processor.swai.SwAiProcessor;
import icurriculum.domain.graduation.processor.swai.strategy.SwAiStrategy;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcessorConfig {

    @Bean
    public Processor<?, ?> swAiProcessor(Map<DepartmentName, SwAiStrategy> swAiStrategyMap) {
        return new SwAiProcessor(swAiStrategyMap);
    }

    @Bean
    public Processor<?, ?> creativityProcessor(
        Map<DepartmentName, CreativityStrategy> creativityStrategyMap) {
        return new CreativityProcessor(creativityStrategyMap);
    }

    @Bean
    public Processor<?, ?> coreProcessor(Map<DepartmentName, CoreStrategy> coreStrategyMap) {
        return new CoreProcessor(coreStrategyMap);
    }

    @Bean
    public Processor<?, ?> generalRequiredProcessor(
        Map<DepartmentName, GeneralRequiredStrategy> generalRequiredStrategyMap) {
        return new GeneralRequiredProcessor(generalRequiredStrategyMap);
    }

    @Bean
    public Processor<?, ?> majorRequiredProcessor(
        Map<DepartmentName, MajorRequiredStrategy> majorRequiredStrategyMap) {
        return new MajorRequiredProcessor(majorRequiredStrategyMap);
    }

    @Bean
    public Processor<?, ?> majorSelectProcessor(
        Map<DepartmentName, MajorSelectStrategy> majorSelectStrategyMap) {
        return new MajorSelectProcessor(majorSelectStrategyMap);
    }

    @Bean
    public Processor<?, ?> generalSelectProcessor(
        Map<DepartmentName, GeneralSelectStrategy> generalSelectStrategyMap) {
        return new GeneralSelectProcessor(generalSelectStrategyMap);
    }

    @Bean
    public Map<ProcessorCategory, Processor<?, ?>> processorMap(List<Processor<?, ?>> processors) {
        Map<ProcessorCategory, Processor<?, ?>> processorMap = new EnumMap<>(
            ProcessorCategory.class);

        for (Processor<?, ?> processor : processors) {
            if (processor instanceof CreativityProcessor) {
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
            if (processor instanceof GeneralRequiredProcessor) {
                processorMap.put(ProcessorCategory.교양필수, processor);
            }

            if (processor instanceof MajorRequiredProcessor) {
                processorMap.put(ProcessorCategory.전공필수, processor);
            }

            if (processor instanceof MajorSelectProcessor) {
                processorMap.put(ProcessorCategory.전공선택, processor);
            }
            if (processor instanceof GeneralSelectProcessor) {
                processorMap.put(ProcessorCategory.교양선택, processor);
            }
        }
        return processorMap;
    }
}
