package icurriculum.domain.graduation.processor.generalselect.config;

import icurriculum.domain.department.DepartmentName;
import icurriculum.domain.graduation.processor.generalselect.strategy.CommonGeneralSelectStrategy;
import icurriculum.domain.graduation.processor.generalselect.strategy.GeneralSelectStrategy;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeneralSelectStrategyConfig {

    @Bean
    public Map<DepartmentName, GeneralSelectStrategy> generalSelectStrategyMap(
        List<GeneralSelectStrategy> strategyList
    ) {
        Map<DepartmentName, GeneralSelectStrategy> strategyMap = new EnumMap<>(
            DepartmentName.class);

        for (GeneralSelectStrategy strategy : strategyList) {

            /*
             * 일반적인 학과
             */
            if (strategy instanceof CommonGeneralSelectStrategy) {
                strategyMap.put(DepartmentName.컴퓨터공학과, strategy);
                //strategyMap.put(DepartmentName.전기공학과, strategy);
                continue;
            }

            /*
             * 만약 로직이 달라진다면, 학과별 구현 클래스 추가
             */

        }
        return strategyMap;
    }

    @Bean
    public GeneralSelectStrategy commonGeneralSelectStrategy() {
        return new CommonGeneralSelectStrategy();
    }

}
