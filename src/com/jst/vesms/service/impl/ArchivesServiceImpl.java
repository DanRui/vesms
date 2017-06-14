package com.jst.vesms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jst.common.hibernate.BaseDAO;
import com.jst.common.hibernate.PropertyFilter;
import com.jst.common.service.BaseServiceImpl;
import com.jst.common.utils.page.Page;
import com.jst.vesms.common.BFGSWebServiceClient;
import com.jst.vesms.dao.IArchivesDao;
import com.jst.vesms.dao.ICallDao;
import com.jst.vesms.dao.IEliminatedApplyDao;
import com.jst.vesms.dao.IPostBaseInfoDao;
import com.jst.vesms.model.Archives;
import com.jst.vesms.model.BatchMain;
import com.jst.vesms.model.EliminatedApply;
import com.jst.vesms.service.ArchivesService;
import com.jst.vesms.service.EliminatedApplyService;
import com.jst.vesms.service.VehicleRecycleService;

@Service("archivesServiceImpl")
public class ArchivesServiceImpl extends BaseServiceImpl 
implements ArchivesService {

	@Resource(name="archivesDao")
	private IArchivesDao archivesDao;
	
	@Resource(name="callDao")
	private ICallDao callDao;
	
	@Resource(name = "eliminatedApplyServiceImpl")
	private EliminatedApplyService eliminatedApplyService;
	
	@Override
	public BaseDAO getHibernateBaseDAO() {
		// TODO Auto-generated method stub
		return archivesDao;
	}


	@Override
	public Archives getById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		Archives archives = (Archives) archivesDao.get(id);
		return archives;
	}


	
	@Override
	public Page getApplyPage(Page page, List<PropertyFilter> list) {
		try {
			page = eliminatedApplyService.getPage(page, list, true, "vehiclePlateTypeName", "vehicleTypeName", "useOfPropertyName", "iolTypeName", "vehicleStatusName");
			page = eliminatedApplyService.getPageExtra(page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}
}
