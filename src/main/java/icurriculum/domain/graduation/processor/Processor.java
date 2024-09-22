package icurriculum.domain.graduation.processor;

import icurriculum.domain.take.Take;
import java.util.LinkedList;

public interface Processor<T, R> {

    R execute(T request, LinkedList<Take> allTakeList);

}
