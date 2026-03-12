package kr.co.kepco.pms.main.login.mapper;

import kr.co.kepco.pms.common.ResultMap;
import kr.co.kepco.pms.main.login.vo.LoginVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 로그인 맵퍼 인터페이스
 */
@Mapper
public interface LoginMapper {

    /**
     * 로그인 정보 조회
     * @param loginVO
     * @return
     */
    ResultMap selectLoginInfo(LoginVO loginVO);
}
