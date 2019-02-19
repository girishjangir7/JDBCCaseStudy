package com.cg.payroll.daoservices;
import java.util.List;
import com.cg.payroll.beans.Associate;
public interface AssociateDAO {
	Associate save(Associate associate);
	Associate findOne(int associateId);
	List <Associate> findAll();
	Associate update(Associate associate);
}
