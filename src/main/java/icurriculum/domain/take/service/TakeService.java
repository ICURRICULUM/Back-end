package icurriculum.domain.take.service;

import icurriculum.domain.member.Member;
import icurriculum.domain.take.Take;
import icurriculum.domain.take.repository.TakeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TakeService {

    private final TakeRepository repository;

    public List<Take> findTakesByMember(Member member) {
        return repository.findByMember(member);
    }

}
