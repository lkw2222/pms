package kr.co.kepco.pms.main.login.service;

import kr.co.kepco.pms.common.ResultMap;
import kr.co.kepco.pms.main.login.mapper.LoginMapper;
import kr.co.kepco.pms.main.login.vo.LoginVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    private final LoginMapper loginMapper;

    @Transactional(readOnly = true)
    public ResultMap selectLoginInfo(LoginVO loginVO) {
        return loginMapper.selectLoginInfo(loginVO);
    }
}
