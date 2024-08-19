package icurriculum.domain.membermajor.service;


import icurriculum.domain.member.Member;
import icurriculum.domain.membermajor.MemberMajor;
import icurriculum.domain.membermajor.repository.MemberMajorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberMajorService {

    private final MemberMajorRepository repository;

    public List<MemberMajor> getMemberMajorsByMember(Member member){
        return repository.findByMember(member);
    }

}
