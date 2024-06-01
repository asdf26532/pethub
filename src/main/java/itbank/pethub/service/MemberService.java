package itbank.pethub.service;

import itbank.pethub.model.MemberDAO;
import itbank.pethub.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    @Autowired
    private MemberDAO dao;

    public MemberVO login(MemberVO input) {
        return dao.selectOne(input);
    }
}
