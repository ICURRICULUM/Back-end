package icurriculum.domain.graduation.processor.core.config;

import icurriculum.domain.department.DepartmentName;
import icurriculum.domain.graduation.processor.core.strategy.CommonCoreStrategy;
import icurriculum.domain.graduation.processor.core.strategy.CoreStrategy;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreStrategyConfig {

    @Bean
    public Map<DepartmentName, CoreStrategy> coreStrategyMap(List<CoreStrategy> strategyList) {
        Map<DepartmentName, CoreStrategy> strategyMap = new EnumMap<>(DepartmentName.class);

        for (CoreStrategy strategy : strategyList) {
            /*
             * 기본 로직으로 잘 수행되는 학과
             */
            if (strategy instanceof CommonCoreStrategy) {
                strategyMap.put(DepartmentName.컴퓨터공학과, strategy);
                continue;
            }

            /*
             * 로직이 달라진다면, 학과별 구현 클래스 추가
             */
        }
        return strategyMap;
    }

    @Bean
    public CoreStrategy commonCoreStrategy() {
        return new CommonCoreStrategy();
    }
}
