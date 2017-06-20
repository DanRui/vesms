package com.jst.vesms.service;

import com.jst.common.service.BaseService;
import com.jst.vesms.model.BatchExport;

public interface ExportBatchService extends BaseService{
	BatchExport getExportPath (String batchNo);
}
