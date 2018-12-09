package org.moons.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.moons.entity.Studentcard;
import org.moons.entity.StudentcardExample;

public interface StudentcardMapper {
    long countByExample(StudentcardExample example);

    int deleteByExample(StudentcardExample example);

    int deleteByPrimaryKey(Short cardid);

    int insert(Studentcard record);

    int insertSelective(Studentcard record);

    List<Studentcard> selectByExample(StudentcardExample example);

    Studentcard selectByPrimaryKey(Short cardid);

    int updateByExampleSelective(@Param("record") Studentcard record, @Param("example") StudentcardExample example);

    int updateByExample(@Param("record") Studentcard record, @Param("example") StudentcardExample example);

    int updateByPrimaryKeySelective(Studentcard record);

    int updateByPrimaryKey(Studentcard record);
}