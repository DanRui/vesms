
package com.jst.vesms.dao;

import com.jst.common.hibernate.BaseDAO;
import com.jst.vesms.model.Attachment;

public interface IAttachmentDao extends BaseDAO<Attachment> {
	
	public void updateAttachment(String applyNo, String type, String status) throws Exception;

}

