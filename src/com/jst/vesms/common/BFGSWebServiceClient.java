
package com.jst.vesms.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import org.springframework.stereotype.Component;

import com.jst.util.WebServiceUtil;
import com.jst.vesms.model.MobileInfo;

@Component
public class BFGSWebServiceClient {
	
	private static final Log log = LogFactory.getLog(BFGSWebServiceClient.class);
	
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
	 * @return List
	 * @throws Exception
	 */
	public static List invoke(String serviceName, String targetNamespace, String methodName, Object[] params, Object[] paramValues) throws Exception {
		log.debug("调用报废公司接口开始");
		RPCServiceClient client = new RPCServiceClient();

		Options options = WebServiceUtil.getDefaultOptions();
		options.setSoapVersionURI(SOAP11Constants.SOAP_ENVELOPE_NAMESPACE_URI);
		options.setTo(new EndpointReference(serviceName));
		options.setAction(targetNamespace + methodName); //C#发布的服务端，需要添加SOAPAction
		client.setOptions(options);
		
		OMFactory factory = OMAbstractFactory.getOMFactory();
		OMNamespace namespace = factory.createOMNamespace(targetNamespace, "");
		OMElement method = factory.createOMElement(methodName, namespace);
		for (int i = 0 ; i < params.length ; i ++) {
			OMElement xmlData = factory.createOMElement((String)params[i], null);
			//xmlData.setText("HS-730000-1323-20150605-8");
			xmlData.setText((String)paramValues[i]);
			method.addChild(xmlData);
		}
		method.build();
		OMElement response = client.sendReceive(method);
		log.debug("获得报废公司接口返回报文：\n" + response.toString());
		Object object = null;
		response.getChildrenWithLocalName("mbs");
		OMElement omResult = response.getFirstElement();
		
		List<Object> list = new ArrayList<Object>();
		
		if (omResult.getLocalName().equals("GetMobileInfoByDateResult") && omResult.getText().equals("0")) {
			
			Iterator iterator = response.getChildrenWithLocalName("mbs");
			
			while (iterator.hasNext())
			{
				OMElement om = (OMElement) iterator.next();
				for (Iterator it = om.getChildElements() ; it.hasNext();) {
					OMElement mobile = (OMElement) it.next();
					Object obj = BeanUtil.processObject(mobile, MobileInfo.class, null, true, new DefaultObjectSupplier(), null);
					list.add(obj);
					System.out.println(obj);
				}
			}
		} else {
			log.error("获取报废公司接口数据异常！错误码:"+ omResult.getText());
			list = null;
		}
		/*for (Iterator iterator2 = om.getChildElements(); iterator2.hasNext();) {
			OMElement type = (OMElement) iterator2.next();
			System.out.println(type.getLocalName()+":"+type.getText());
		}*/
		log.debug("调用报废公司接口结束");
		return list == null ? null : list;
	}
	
	public static void main(String[] args) throws Exception {
		List<MobileInfo> recycleInfoEntity =  BFGSWebServiceClient.invoke(SERVICE_URL_PREFIX, TARGET_NAMESPACE, "GetMobileInfoByDate", new String[] {"start", "end"}, new String[] {"2017-05-01 00:00:00", "2017-05-10 23:59:59"});

		System.out.println(recycleInfoEntity);
	}

}

