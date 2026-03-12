package kr.co.kepco.pms.main.sample.mapper;

import kr.co.kepco.pms.common.ResultMap;
import kr.co.kepco.pms.main.sample.vo.SampleVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SampleMapper {

    /**
     * sample list
     * @param sampleVO
     * @return
     */
    List<ResultMap> selectSampleList(SampleVO sampleVO);
}
