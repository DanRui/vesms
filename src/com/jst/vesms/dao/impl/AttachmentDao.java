
package com.jst.vesms.dao.impl;

import java.util.List;

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

	@Override
	public void updateAttachment(String applyNo, String type, String status)
			throws Exception {
		List<Attachment> attachments = this.getByPropertys(new String[] {"applyNo", "type", "status"},  new Object[] {applyNo, type, "1"},  null);
		for (Attachment attachment : attachments) {
			attachment.setStatus(status);
			this.update(attachment);
		}
		
	}

	
}

