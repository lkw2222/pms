package kr.co.kepco.pms.main.sample.service;

import kr.co.kepco.pms.common.ResultMap;
import kr.co.kepco.pms.main.sample.mapper.SampleMapper;
import kr.co.kepco.pms.main.sample.vo.SampleVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SampleService {

    private final SampleMapper sampleMapper;

    /**
     * sample list
     * @param sampleVO
     * @return
     */
    @Transactional(readOnly = true)
    public List<ResultMap> selectSampleList(SampleVO sampleVO) {
        return sampleMapper.selectSampleList(sampleVO);
    }
}
