1.��ѯĳ���꼶�� ѧ������
���룺�꼶
��������꼶��ѧ������

create or replace procedure queryCountByGradeWithProcedure(gName in varchar, scount out number )
as
begin 
	select count(*) into scount from student where graname = gname ;
end;
/




2.����ѧ��ɾ��ѧ��
create or replace procedure deleteStuBynoWithProcedure(sno in number)
as
begin
	delete from student where stuno = sno  ;
end;
/













