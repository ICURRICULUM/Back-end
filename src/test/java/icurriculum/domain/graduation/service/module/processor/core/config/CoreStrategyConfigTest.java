package icurriculum.domain.graduation.service.module.processor.core.config;

import static org.assertj.core.api.Assertions.assertThat;

import icurriculum.domain.department.DepartmentName;
import icurriculum.domain.graduation.service.module.processor.core.strategy.CommonCoreStrategy;
import icurriculum.domain.graduation.service.module.processor.core.strategy.CoreStrategy;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class CoreStrategyConfigTest {

    @Mock
    private CommonCoreStrategy commonCoreStrategy;

    @InjectMocks
    private CoreStrategyConfig coreStrategyConfig;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("CoreStrategyMap 생성 테스트 - CommonCoreStrategy가 할당되는지 확인")
    void CoreStrategyMap_생성_테스트_CommonCoreStrategy_할당_확인() {
        // given
        List<CoreStrategy> strategyList = List.of(commonCoreStrategy);

        // when
        Map<DepartmentName, CoreStrategy> strategyMap = coreStrategyConfig.coreStrategyMap(
            strategyList
        );

        // then
        assertThat(strategyMap)
            .containsEntry(DepartmentName.컴퓨터공학과, commonCoreStrategy);
    }
}
