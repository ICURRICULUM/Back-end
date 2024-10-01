package icurriculum.domain.graduation.service.module.processor.generalrequired.config;

import icurriculum.domain.department.DepartmentName;
import icurriculum.domain.graduation.service.module.processor.generalrequired.strategy.CommonGeneralRequiredStrategy;
import icurriculum.domain.graduation.service.module.processor.generalrequired.strategy.GeneralRequiredStrategy;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeneralRequiredStrategyConfig {

    @Bean
    public Map<DepartmentName, GeneralRequiredStrategy> generalRequiredStrategyMap(
        List<GeneralRequiredStrategy> strategyList
    ) {
        Map<DepartmentName, GeneralRequiredStrategy> strategyMap = new EnumMap<>(
            DepartmentName.class);

        for (GeneralRequiredStrategy strategy : strategyList) {

            /*
             * [일반 학과]
             *
             * - 모든 필수과목을 다 들어야 하는 학과
             * - 영어 처리는 여기서 가능
             */
            if (strategy instanceof CommonGeneralRequiredStrategy) {
                strategyMap.put(DepartmentName.컴퓨터공학과, strategy);
                // strategyMap.put(DepartmentName.전기공학과, strategy);
                continue;
            }

            /*
             * 만약 로직이 달라진다면, 학과별 구현 클래스 추가
             */

        }
        return strategyMap;
    }

    @Bean
    public GeneralRequiredStrategy commonGeneralRequiredStrategy() {
        return new CommonGeneralRequiredStrategy();
    }

}
