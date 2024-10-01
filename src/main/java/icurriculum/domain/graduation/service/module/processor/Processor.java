package icurriculum.domain.graduation.service.module.processor;

import icurriculum.domain.take.Take;
import java.util.LinkedList;

public interface Processor<T, R> {

    R execute(T request, LinkedList<Take> allTakeList);

}
