/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.bnl.racf.ps.dashboard.db.object_manipulators;

import gov.bnl.racf.ps.dashboard.db.data_objects.*;
import java.util.*;
import javax.persistence.Id;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

/**
 * Utility class to convert perfsonar objects into their JSON representation
 *
 * @author tomw
 */
public class JsonConverter {

    /**
     * static method to convert host to JsonObject
     *
     * @param host
     * @return
     */
    public static JSONObject psHost2Json(PsHost host) {
        JSONObject json = new JSONObject();

        json.put(PsHost.ID, host.getId());
        json.put(PsHost.HOSTNAME, host.getHostname());
        json.put(PsHost.IPV4, host.getIpv4());
        json.put(PsHost.IPV6, host.getIpv6());

        JSONArray services = new JSONArray();
        Iterator<PsService> iter = host.serviceIterator();
        while (iter.hasNext()) {
            PsService service = (PsService) iter.next();
            int serviceId = service.getId();
            services.add(serviceId);
        }
        json.put(PsHost.SERVICES, services);

        return json;
    }

    /**
     * convert host object to Json
     *
     * @param host
     * @return
     */
    public static JSONObject toJson(PsHost host) {
        return psHost2Json(host);
    }

    /**
     * convert parameter info object into JSON
     *
     * @param parameterInfo
     * @return
     */
    public static JSONObject toJson(PsParameterInfo parameterInfo) {
        JSONObject json = new JSONObject();
        json.put(PsParameterInfo.DESCRIPTION, parameterInfo.getDescription());
        json.put(PsParameterInfo.UNIT, parameterInfo.getUnit());
        return json;
    }

    public static JSONObject toJson(PsServiceType serviceType) {
        JSONObject json = new JSONObject();
        // warning: externally id means 'service type id'
        json.put(PsServiceType.ID, serviceType.getServiceTypeId());
        // internal id is the id in the database
        json.put(PsServiceType.INTERNAL_ID, serviceType.getId());
        json.put(PsServiceType.JOB_TYPE, serviceType.getJobType());
        json.put(PsServiceType.NAME, serviceType.getName());

        // get serviceParameterInfo
        TreeMap<String, PsParameterInfo> serviceParameterInfo =
                serviceType.getServiceParameterInfo();
        JSONObject serviceParameterInfoJson = new JSONObject();
        if (serviceParameterInfo != null) {
            Iterator keyIterator = serviceParameterInfo.keySet().iterator();
            while (keyIterator.hasNext()) {
                String currentParameterName = (String) keyIterator.next();
                PsParameterInfo currentParameterInfo =
                        serviceParameterInfo.get(currentParameterName);
                JSONObject currentParameterInfoAsJson = toJson(currentParameterInfo);
                serviceParameterInfoJson.put(currentParameterName, currentParameterInfoAsJson);
            }
        }
        json.put(PsServiceType.SERVICE_PARAMETER_INFO, serviceParameterInfoJson);

        // get result parameter info 
        TreeMap<String, PsParameterInfo> resultParameterInfo =
                serviceType.getResultParameterInfo();
        JSONObject resultParameterInfoJson = new JSONObject();

        if (resultParameterInfo != null) {
            Iterator keyIterator2 = resultParameterInfo.keySet().iterator();
            while (keyIterator2.hasNext()) {
                String currentParameterName = (String) keyIterator2.next();
                PsParameterInfo currentParameterInfo =
                        resultParameterInfo.get(currentParameterName);
                JSONObject currentParameterInfoAsJson = toJson(currentParameterInfo);
                resultParameterInfoJson.put(currentParameterName, currentParameterInfoAsJson);
            }
        }
        json.put(PsServiceType.RESULT_PARAMETER_INFO, resultParameterInfoJson);
        return json;
    }
    
    /**
     * Convert perfsonar service to Json object
     *
     * @param service
     * @return
     */
    public static JSONObject toJson(PsService service) {

        JSONObject json = new JSONObject();

        if (service != null) {

            json.put(PsService.ID, service.getId());
            json.put(PsService.TYPE, service.getType());
            json.put(PsService.NAME, service.getName());
            json.put(PsService.DESCRIPTION, service.getDescription());


            // add service parameters

            if (service.getParameters() != null) {
                JSONObject serviceParameters = serviceParametersAsJson(service);
                json.put(PsService.PARAMETERS, serviceParameters);
            }


            json.put(PsService.CHECK_INTERVAL, new Integer(service.getCheckInterval()));
            json.put(PsService.RUNNING, service.isRunning());
            json.put(PsService.PREV_CHECK_TIME,
                    IsoDateConverter.dateToString(service.getPrevCheckTime()));
            json.put(PsService.NEXT_CHECK_TIME,
                    IsoDateConverter.dateToString(service.getNextCheckTime()));
            json.put(PsService.RUNNING_SINCE,
                    IsoDateConverter.dateToString(service.getRunningSince()));
            json.put(PsService.TIMEOUT, service.getTimeout());


            // add result
            PsServiceResult result = service.getResult();
            JSONObject jsonResult = toJson(result);
            json.put(PsService.RESULT, jsonResult);

        }

        return json;
    }
    
    /**
     * get service parameters of this particular service and convert them into
     * JSON object
     *
     * @param service
     * @return
     */
    public static JSONObject serviceParametersAsJson(PsService service) {
        JSONObject serviceParameters = new JSONObject();

        Iterator iter = service.getParameters().keySet().iterator();
        while (iter.hasNext()) {
            String key = (String) iter.next();

            Object value = service.getParameters().get(key);
            serviceParameters.put(key, value);
        }
        return serviceParameters;
    }
    
    /**
     * convert service result object to JSON
     * @param result
     * @return 
     */
    public static JSONObject toJson(PsServiceResult result) {
        JSONObject json = new JSONObject();

        if (result != null) {
            json.put(PsServiceResult.ID, result.getId());
            json.put(PsServiceResult.JOB_ID, result.getJob_id());
            json.put(PsServiceResult.SERVICE_ID, result.getService_id());
            json.put(PsServiceResult.STATUS, result.getStatus());
            json.put(PsServiceResult.MESSAGE, result.getMessage());

            json.put(PsServiceResult.TIME, IsoDateConverter.dateToString(result.getTime()));

            JSONObject parameters = new JSONObject();
            TreeMap<String, Object> treeMap = result.getParameters();
            for (Map.Entry<String, Object> entry : treeMap.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                parameters.put(key, value);
            }
            json.put(PsServiceResult.PARAMETERS, parameters);

        }

        return json;
    }
}
