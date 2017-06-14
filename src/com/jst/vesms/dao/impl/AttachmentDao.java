
package com.jst.vesms.dao.impl;

import org.springframework.stereotype.Repository;

import com.jst.common.hibernate.HibernateBaseDAO;
import com.jst.vesms.dao.IAttachmentDao;
import com.jst.vesms.model.Attachment;

@Repository("attachmentDao")
public class AttachmentDao extends HibernateBaseDAO<Attachment> implements
		IAttachmentDao {
	
	private static final String modelName = Attachment.class.getName();
	
	@Override
	public String getModelName() {
		
		return modelName;
	}

	
}

