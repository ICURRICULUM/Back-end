package icurriculum.domain.graduation.service.module.processor.swai.config;

import icurriculum.domain.department.DepartmentName;
import icurriculum.domain.graduation.service.module.processor.swai.strategy.CommonSwAiStrategy;
import icurriculum.domain.graduation.service.module.processor.swai.strategy.SwAiStrategy;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwAiStrategyConfig {

    @Bean
    public Map<DepartmentName, SwAiStrategy> swAiStrategyMap(
        List<SwAiStrategy> strategyList
    ) {
        Map<DepartmentName, SwAiStrategy> strategyMap = new EnumMap<>(DepartmentName.class);

        for (SwAiStrategy strategy : strategyList) {

            /*
             * 일반 학과
             */
            if (strategy instanceof CommonSwAiStrategy) {
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
    public SwAiStrategy commonSwAiStrategy() {
        return new CommonSwAiStrategy();
    }

}
