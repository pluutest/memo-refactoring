package com.memo.studygroup.nohhs.db;

import java.util.List;

import com.memo.studygroup.nohhs.adapter.item.MemoVO;

/**
 * Created by nohhs on 2015-07-06.
 */
public interface CRUDOperations {
	long insert(MemoVO memoVO);
	int update(MemoVO memoVO);
	void delete(MemoVO memoVo);
	MemoVO read(int id);
	List<MemoVO> readAll();
}