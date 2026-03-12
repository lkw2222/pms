package kr.co.kepco.pms.main.sample.controller;

import kr.co.kepco.pms.common.ResultMap;
import kr.co.kepco.pms.main.sample.service.SampleService;
import kr.co.kepco.pms.main.sample.vo.SampleVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SampleController {

    private final SampleService sampleService;

    /**
     * sample list
     * @param sampleVO
     * @return
     */
    @PostMapping("/api/sample/list")
    public ResponseEntity<?> selectList(@RequestBody SampleVO sampleVO) {
        List<ResultMap> list = sampleService.selectSampleList(sampleVO);
        return ResponseEntity.ok(list);
    }

}
