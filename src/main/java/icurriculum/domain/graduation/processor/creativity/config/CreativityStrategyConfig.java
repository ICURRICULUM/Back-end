package icurriculum.domain.graduation.processor.creativity.config;

import icurriculum.domain.department.DepartmentName;
import icurriculum.domain.graduation.processor.creativity.strategy.CommonCreativityStrategy;
import icurriculum.domain.graduation.processor.creativity.strategy.CreativityStrategy;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CreativityStrategyConfig {

    @Bean
    public Map<DepartmentName, CreativityStrategy> creativityStrategyMap(
        List<CreativityStrategy> strategyList
    ) {
        Map<DepartmentName, CreativityStrategy> strategyMap = new EnumMap<>(DepartmentName.class);

        for (CreativityStrategy strategy : strategyList) {

            /*
             * 일반 학과
             */
            if (strategy instanceof CommonCreativityStrategy) {
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
    public CreativityStrategy commonCreativityStrategy() {
        return new CommonCreativityStrategy();
    }

}
