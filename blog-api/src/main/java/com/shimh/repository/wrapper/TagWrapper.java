package com.shimh.repository.wrapper;

import java.util.List;

import com.shimh.vo.TagVO;

/**
 * @author CSE
 * <p>
 * 2019年1月25日
 */
public interface TagWrapper {

    List<TagVO> findAllDetail();

    TagVO getTagDetail(Integer tagId);
}
