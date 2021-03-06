/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.racf.bnl.ps.dashboard.PsApi;

/**
 *
 * @author tomw
 */
public class PsApi {
     // host API commands
    public static final String HOST="hosts";   
    public static final String HOST_ADD_SERVICE_TYPE_COMMAND = "addservicetype";
    public static final String HOST_REMOVE_SERVICE_TYPE_COMMAND = "removeservicetype";
    public static final String HOST_REMOVE_SERVICE_ID_COMMAND = "removeserviceid";
    public static final String HOST_ADD_LATENCY_SERVICES_COMMAND = "addlatencyservices";
    public static final String HOST_ADD_THROUGHPUT_SERVICES_COMMAND = "addthroughputservices";
    public static final String HOST_ADD_ALL_SERVICES_COMMAND = "addallservices";
    public static final String HOST_REMOVE_ALL_SERVICES_COMMAND = "removeallservices";
    
    public static final String SERVICE="services";  
    
    public static final String SITE="sites";  
    public static final String SITE_ADD_HOST_IDS="addhostids";
    public static final String SITE_REMOVE_HOST_IDS="removehostids";
    
    public static final String CLOUD="clouds";
    public static final String CLOUD_ADD_SITE_IDS="addsiteids";
    public static final String CLOUD_REMOVE_SITE_IDS="removesiteids";
    
    public static final String MATRIX="matrices";
    public static final String MATRIX_ADD_HOST_IDS="addhostids";
    public static final String MATRIX_REMOVE_HOST_IDS="removehostids";
    
    // add or remove columns. Those operations are not implemented yet
    public static final String MATRIX_ADD_COLUMN_HOST_IDS="addcolumnhostids";
    public static final String MATRIX_REMOVE_COLUMN_HOST_IDS="removecolumnhostids";
    public static final String MATRIX_ADD_ROW_HOST_IDS="addrowhostids";
    public static final String MATRIX_REMOVE_ROW_HOST_IDS="removerowhostids";
    
    // known service types, in the future I may provide a URL from which you
    // could obtain a valid list, but for the time being this list should be enough
    
    //TODO this list is repeated in PsServiceTypeFactory, maybe it should be merged
    public static String BWCTL_PORT_4823 = "bwctl_port_4823";
    public static String BWCTL_PORT_8570 = "bwctl_port_8570";
    public static String CHECK_LOOKUP_SERVICE = "CheckLookupService";
    public static String NDT_PORT_3001 = "NDT_port_3001";
    public static String NDT_PORT_7123 = "NDT_port_7123";
    public static String NPAD_PORT_8000 = "NPAD_port_8000";
    public static String NPAD_PORT_8001 = "NPAD_port_8001";
    public static String OWP_861 = "owp_861";
    public static String OWP_8569 = "owp_8569";
    public static String PERFSONAR_PSB = "perfSONAR_pSB";
    public static String LATENCY = "latency";
    public static String THROUGHPUT = "throughput";
    public static String TRACEROUTE = "traceroute";
    
    
}
