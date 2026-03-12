package kr.co.kepco.pms.main.login.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.co.kepco.pms.common.ResultMap;
import kr.co.kepco.pms.main.login.service.LoginService;
import kr.co.kepco.pms.main.login.vo.LoginVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 로그인 컨트롤러 클래스
 */
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final PasswordEncoder passwordEncoder;

    /**
     * 로그인 요청
     * @param loginVO
     * @param httpServletRequest
     * @return
     */
    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody LoginVO loginVO, HttpServletRequest httpServletRequest) {
        ResultMap loginInfo = loginService.selectLoginInfo(loginVO);
        String requestPw = loginVO.getPassword();

        if(loginInfo != null) {
            String dbPw = loginVO.getPassword().toString();

            if(passwordEncoder.matches(requestPw, dbPw)) {
                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginVO.getId(), null, authorities);

                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                securityContext.setAuthentication(authToken);

                SecurityContextHolder.setContext(securityContext);

                HttpSession session = httpServletRequest.getSession(true);
                session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);

                return ResponseEntity.ok().body("로그인 성공");
            } else {
                return ResponseEntity.status(401).body("아이디 또는 비밀번호가 틀렸습니다.");
            }
        } else {
            return ResponseEntity.status(401).body("아이디 또는 비밀번호가 틀렸습니다.");
        }
    }

    /**
     * 로그아웃 요청
     * @param httpServletRequest
     * @return
     */
    @PostMapping("/api/logout")
    public ResponseEntity<?> logout(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);

        if(session != null){
            session.invalidate();
        }
        SecurityContextHolder.clearContext();

        return ResponseEntity.ok().body("로그아웃 성공");
    }
}
