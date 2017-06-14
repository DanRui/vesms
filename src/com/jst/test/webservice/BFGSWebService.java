
package com.jst.test.webservice;

import java.util.Iterator;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.soap.SOAP11Constants;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.databinding.utils.BeanUtil;
import org.apache.axis2.engine.DefaultObjectSupplier;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jst.util.WebServiceUtil;
import com.jst.vesms.model.RecycleInfoEntity;

public class BFGSWebService {
	
	private static final Log log = LogFactory.getLog(BFGSWebService.class);
	
	//服务地址前缀
	private static final String SERVICE_URL_PREFIX = "http://hbc.szjdc.net/mobileservice.asmx";
	
	//目标命名空间
	private static final String TARGET_NAMESPACE = "http://hbc.szjdc.net/";
	
	/**
	 * @see 调用接口
	 * @param serviceName
	 * @param targetNamespace
	 * @param methodName
	 * @param params
	 * @param paramValues
	 * @return Object
	 * @throws Exception
	 */
	public static Object invoke(String serviceName, String targetNamespace, String methodName, Object[] params, Object[] paramValues) throws Exception {
		RPCServiceClient client = new RPCServiceClient();

		Options options = WebServiceUtil.getDefaultOptions();
		options.setSoapVersionURI(SOAP11Constants.SOAP_ENVELOPE_NAMESPACE_URI);
		options.setTo(new EndpointReference(serviceName));
		options.setAction(targetNamespace + methodName);
		client.setOptions(options);
		
		OMFactory factory = OMAbstractFactory.getOMFactory();
		OMNamespace namespace = factory.createOMNamespace(targetNamespace, "");
		OMElement method = factory.createOMElement(methodName, namespace);
		OMElement xmlData = factory.createOMElement((String)params[0], null);
//		xmlData.setText("HS-730000-1323-20150605-8");
		xmlData.setText((String)paramValues[0]);
		method.addChild(xmlData);
		method.build();
		OMElement response = client.sendReceive(method);
		Object object = null;
		for (Iterator iterator = response.getChildElements(); iterator.hasNext();) {
			OMElement om = (OMElement) iterator.next();
			object = BeanUtil.processObject(om, RecycleInfoEntity.class, null, true, new DefaultObjectSupplier(), null);
			/*for (Iterator iterator2 = om.getChildElements(); iterator2.hasNext();) {
				OMElement type = (OMElement) iterator2.next();
				System.out.println(type.getLocalName()+":"+type.getText());
			}*/
		}
		return object == null ? null : object;
	}
	
	public static void main(String[] args) throws Exception {
		RecycleInfoEntity recycleInfoEntity =  (RecycleInfoEntity)BFGSWebService.invoke(SERVICE_URL_PREFIX, TARGET_NAMESPACE, "GetMobileInfo1", new String[] {"gjhszm"}, new String[] {"BP17226897"});

		System.out.println(recycleInfoEntity);
	}

}

