package icurriculum.domain.take.service;

import icurriculum.domain.member.Member;
import icurriculum.domain.membermajor.MajorType;
import icurriculum.domain.take.Take;
import icurriculum.domain.take.repository.TakeRepository;
import java.util.LinkedList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TakeService {

    private final TakeRepository repository;

    public List<Take> getTakeListByMember(Member member) {
        return repository.findByMember(member);
    }

    public LinkedList<Take> getTakeListByMemberAndMajorType(Member member, MajorType majorType) {
        List<Take> TakeList = repository.findByMemberAndMajorType(member, majorType);
        return new LinkedList<>(TakeList);
    }

}
