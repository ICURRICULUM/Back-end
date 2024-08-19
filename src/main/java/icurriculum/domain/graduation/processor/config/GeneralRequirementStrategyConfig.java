package icurriculum.domain.graduation.processor.config;

import icurriculum.domain.department.DepartmentName;
import icurriculum.domain.graduation.processor.strategy.generalrequirement.strategy.GeneralRequirementStrategy;
import icurriculum.domain.graduation.processor.strategy.generalrequirement.strategy.AllRequiredStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static icurriculum.domain.department.DepartmentName.*;

@Configuration
public class GeneralRequirementStrategyConfig {

    @Bean
    public Map<DepartmentName, GeneralRequirementStrategy> generalRequirementStrategyMap(List<GeneralRequirementStrategy> strategies) {
        Map<DepartmentName, GeneralRequirementStrategy> strategyMap = new EnumMap<>(DepartmentName.class);

        for (GeneralRequirementStrategy strategy : strategies) {

            /**
             * 모든 필수과목을 다 들어야 하는 학과
             */
            if (strategy instanceof AllRequiredStrategy) {
                strategyMap.put(컴퓨터공학과, strategy);
                strategyMap.put(전기공학과, strategy);
            }

            /**
             * 만약 로직이 달라진다면, 학과별 구현 클래스 추가
             */

        }
        return strategyMap;
    }

    @Bean
    public GeneralRequirementStrategy allRequiredStrategy() {
        return new AllRequiredStrategy();
    }

}
