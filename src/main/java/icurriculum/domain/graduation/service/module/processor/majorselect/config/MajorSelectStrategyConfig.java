package icurriculum.domain.graduation.service.module.processor.majorselect.config;

import icurriculum.domain.department.DepartmentName;
import icurriculum.domain.graduation.service.module.processor.majorselect.strategy.CommonMajorSelectStrategy;
import icurriculum.domain.graduation.service.module.processor.majorselect.strategy.MajorSelectStrategy;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MajorSelectStrategyConfig {

    @Bean
    public Map<DepartmentName, MajorSelectStrategy> majorSelectStrategyMap(
        List<MajorSelectStrategy> strategyList
    ) {
        Map<DepartmentName, MajorSelectStrategy> strategyMap = new EnumMap<>(DepartmentName.class);

        for (MajorSelectStrategy strategy : strategyList) {

            /*
             * 모든 필수과목을 다 들어야 하는 학과
             */
            if (strategy instanceof CommonMajorSelectStrategy) {
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
    public MajorSelectStrategy commonMajorSelectStrategy() {
        return new CommonMajorSelectStrategy();
    }

}
