package icurriculum.domain.graduation.service.module.processor.config;

import static org.assertj.core.api.Assertions.assertThat;

import icurriculum.domain.graduation.service.module.processor.Processor;
import icurriculum.domain.graduation.service.module.processor.core.CoreProcessor;
import icurriculum.domain.graduation.service.module.processor.swai.SwAiProcessor;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProcessorConfigTest {

    @Mock
    private SwAiProcessor swAiProcessor;

    @Mock
    private CoreProcessor coreProcessor;

    @InjectMocks
    private ProcessorConfig processorConfig;

    @Test
    @DisplayName("ProcessorConfig에서 processorMap을 구성하여 정상적으로 반환하는지 테스트")
    void processorMap_구성_테스트() {
        // given
        List<Processor<?, ?>> processorList = List.of(swAiProcessor, coreProcessor);

        // when
        Map<ProcessorCategory, Processor<?, ?>> processorMap = processorConfig.processorMap(
            processorList);

        // then
        assertThat(processorMap).isNotNull();
        assertThat(processorMap).containsEntry(ProcessorCategory.SW_AI, swAiProcessor);
        assertThat(processorMap).containsEntry(ProcessorCategory.핵심교양, coreProcessor);
    }
}