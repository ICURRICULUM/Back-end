package icurriculum.domain.graduation.processor.util;

import icurriculum.domain.graduation.processor.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class ProcessorUtilsTest {

    @Autowired
    private Map<ProcessorCategory, Processor<?, ?>> processorMap;

    @Test
    @DisplayName("ProcessorCategory에 해당하는 Processor를 정상적으로 반환")
    void getProcessorByCategory_ShouldReturnCorrectProcessor() {
        // when
        Processor<?, ?> creativeProcessor = ProcessorUtils.getProcessorByCategory(processorMap, ProcessorCategory.창의);
        Processor<?, ?> swAiProcessor = ProcessorUtils.getProcessorByCategory(processorMap, ProcessorCategory.SW_AI);
        Processor<?, ?> coreProcessor = ProcessorUtils.getProcessorByCategory(processorMap, ProcessorCategory.핵심교양);
        Processor<?, ?> generalRequirementProcessor = ProcessorUtils.getProcessorByCategory(processorMap, ProcessorCategory.교양필수);

        // then
        assertThat(creativeProcessor).isInstanceOf(CreativeProcessor.class);
        assertThat(swAiProcessor).isInstanceOf(SwAiProcessor.class);
        assertThat(coreProcessor).isInstanceOf(CoreProcessor.class);
        assertThat(generalRequirementProcessor).isInstanceOf(GeneralRequirementProcessor.class);
    }

    @Test
    @DisplayName("존재하지 않는 ProcessorCategory에 대해 예외를 발생")
    void getProcessorByCategory_ShouldThrowExceptionForUnknownCategory() {
        /**
         * Todo 구현 예정
         * 코드 수정 발생될 것
         */
        // when & then
        assertThatThrownBy(() -> ProcessorUtils.getProcessorByCategory(processorMap, ProcessorCategory.전공선택))
                .isInstanceOf(RuntimeException.class);
    }
}
