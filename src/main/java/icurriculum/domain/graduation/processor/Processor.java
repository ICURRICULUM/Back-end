package icurriculum.domain.graduation.processor;

import icurriculum.domain.take.Take;

import java.util.List;

public interface Processor<T, R> {
    R execute(T requirement, List<Take> takes);

}
