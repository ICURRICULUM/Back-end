package icurriculum.global.util;

import static org.assertj.core.api.Assertions.assertThat;

import icurriculum.domain.graduation.processor.Processor;
import icurriculum.domain.graduation.processor.config.ProcessorCategory;
import icurriculum.domain.graduation.processor.core.CoreProcessor;
import icurriculum.domain.graduation.processor.creativity.CreativityProcessor;
import icurriculum.domain.graduation.processor.generalrequired.GeneralRequiredProcessor;
import icurriculum.domain.graduation.processor.majorrequired.MajorRequiredProcessor;
import icurriculum.domain.graduation.processor.majorselect.MajorSelectProcessor;
import icurriculum.domain.graduation.processor.swai.SwAiProcessor;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProcessorUtilsTest {

    @Autowired
    private Map<ProcessorCategory, Processor<?, ?>> processorMap;

    @Test
    @DisplayName("ProcessorCategory에 해당하는 Processor를 정상적으로 반환")
    void ProcessorCategory_해당하는_Processor를_정상반환() {
        // when
        Processor<?, ?> creativeProcessor = ProcessorUtils.get(processorMap, ProcessorCategory.창의);
        Processor<?, ?> swAiProcessor = ProcessorUtils.get(processorMap, ProcessorCategory.SW_AI);
        Processor<?, ?> coreProcessor = ProcessorUtils.get(processorMap, ProcessorCategory.핵심교양);
        Processor<?, ?> generalRequiredProcessor = ProcessorUtils.get(processorMap,
            ProcessorCategory.교양필수);
        Processor<?, ?> majorSelectProcessor = ProcessorUtils.get(processorMap,
            ProcessorCategory.전공선택);
        Processor<?, ?> majorRequiredProcessor = ProcessorUtils.get(processorMap,
            ProcessorCategory.전공필수);

        // then
        assertThat(creativeProcessor).isInstanceOf(CreativityProcessor.class);
        assertThat(swAiProcessor).isInstanceOf(SwAiProcessor.class);
        assertThat(coreProcessor).isInstanceOf(CoreProcessor.class);
        assertThat(generalRequiredProcessor).isInstanceOf(GeneralRequiredProcessor.class);
        assertThat(majorSelectProcessor).isInstanceOf(MajorSelectProcessor.class);
        assertThat(majorRequiredProcessor).isInstanceOf(MajorRequiredProcessor.class);
    }

}
