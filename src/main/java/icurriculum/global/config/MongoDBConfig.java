package icurriculum.global.config;

import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@Configuration
@EnableMongoAuditing
public class MongoDBConfig {

    private static final String NO_SESSION = "no session";

    /**
     * Todo: 추후 로그인 기능 구현 시 회원의 ID 값을 넣어주기
     */
    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> Optional.of(NO_SESSION);
    }
}