/**
 * Project Name:pay-protocol
 * File Name:Xml.java
 * Package Name:cn.swiftpass.pay.protocol
 * Date:2014-8-10下午10:48:21
 *
*/

package com.fanfan.alon.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

/**
 * 功能描述:XML的工具方法
 * @param:
 * @return:
 * @auther: zoujiulong
 * @date: 2018/9/6   14:59
 */
public class XmlUtils {
    
    /**
     * 功能描述:request转字符串
     * @param:
     * @return:
     * @auther: zoujiulong
     * @date: 2018/9/6   15:00
     */
    public static String parseRequst(HttpServletRequest request){
        String body = "";
        BufferedReader br = null;
        try {
            ServletInputStream inputStream = request.getInputStream(); 
            br = new BufferedReader(new InputStreamReader(inputStream));
            while(true){
                String info = br.readLine();
                if(info == null){
                    break;
                }
                if(body == null || "".equals(body)){
                    body = info;
                }else{
                    body += info;
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
        	if(br != null){
        		try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        }
        return body;
    }
    
    @SuppressWarnings("rawtypes")
	public static String parseXML(SortedMap<String, String> parameters) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            String v = (String)entry.getValue();
            if (null != v && !"".equals(v) && !"appkey".equals(k)) {
                sb.append("<" + k + ">" + parameters.get(k) + "</" + k + ">\n");
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }
    /**
     * 功能描述:从request中获得参数Map，并返回可读的Map
     * @param:
     * @return:
     * @auther: zoujiulong
     * @date: 2018/9/6   15:01
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static SortedMap getParameterMap(HttpServletRequest request) {
        // 参数Map
        Map properties = request.getParameterMap();
        // 返回值Map
        SortedMap returnMap = new TreeMap();
        Iterator entries = properties.entrySet().iterator();
        Map.Entry entry;
        String name = "";
        String value = "";
        while (entries.hasNext()) {
            entry = (Map.Entry) entries.next();
            name = (String) entry.getKey();
            Object valueObj = entry.getValue();
            if(null == valueObj){
                value = "";
            }else if(valueObj instanceof String[]){
                String[] values = (String[])valueObj;
                for(int i=0;i<values.length;i++){
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length()-1);
            }else{
                value = valueObj.toString();
            }
            returnMap.put(name, value.trim());
        }
        return returnMap;
    }
    
    /**
     * 功能描述:转XMLmap
     * @param:
     * @return:
     * @auther: zoujiulong
     * @date: 2018/9/6   15:01
     */
    public static Map<String, String> toMap(byte[] xmlBytes,String charset) throws Exception{
        SAXReader reader = new SAXReader(false);
        InputSource source = new InputSource(new ByteArrayInputStream(xmlBytes));
        source.setEncoding(charset);
        Document doc = reader.read(source);
        Map<String, String> params = XmlUtils.toMap(doc.getRootElement());
        return params;
    }
    
    /**
     * 功能描述:转MAP
     * @param:
     * @return:
     * @auther: zoujiulong
     * @date: 2018/9/6   15:01
     */
    @SuppressWarnings("unchecked")
	public static Map<String, String> toMap(Element element){
        Map<String, String> rest = new HashMap<String, String>();
        List<Element> els = element.elements();
        for(Element el : els){
            rest.put(el.getName().toLowerCase(), el.getTextTrim());
        }
        return rest;
    }
    
    public static String toXml(Map<String, String> params,boolean cdata){
        StringBuilder buf = new StringBuilder();
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        buf.append("<xml>");
        for(String key : keys){
            buf.append("<").append(key).append(">");
            if(cdata){
            	  buf.append("<![CDATA[");
            }
            buf.append(params.get(key));
            if(cdata){
            	 buf.append("]]>");
            }
            buf.append("</").append(key).append(">\n");
        }
        buf.append("</xml>");
        return buf.toString();
    }
}

