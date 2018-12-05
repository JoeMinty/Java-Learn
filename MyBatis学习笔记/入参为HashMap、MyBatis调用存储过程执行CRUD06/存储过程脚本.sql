1.查询某个年级的 学生总数
输入：年级
输出：该年级的学生总数

create or replace procedure queryCountByGradeWithProcedure(gName in varchar, scount out number )
as
begin 
	select count(*) into scount from student where graname = gname ;
end;
/




2.根据学号删除学生
create or replace procedure deleteStuBynoWithProcedure(sno in number)
as
begin
	delete from student where stuno = sno  ;
end;
/













