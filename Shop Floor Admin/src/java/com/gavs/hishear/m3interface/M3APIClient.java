package com.gavs.hishear.m3interface;

import MvxAPI.MvxSockJ;

import com.gavs.hishear.EventLog.LogTools;
import com.gavs.hishear.constant.M3Parameter;
import com.gavs.hishear.web.domain.ApplicationPropertyBean;
import com.gavs.hishear.web.domain.ErrorLog;
import org.apache.log4j.Logger;

import java.net.SocketException;
import java.util.*;

/**
 * The Class M3APIClient.
 */
public class M3APIClient {

    private static final String COMMA = ",";
    private ApplicationPropertyBean applicationProperty;
    Logger logger = Logger.getLogger(M3APIClient.class);

    public void setApplicationProperty(ApplicationPropertyBean applicationProperty) {
        this.applicationProperty = applicationProperty;
    }

    public List<Map<M3Parameter, String>> execute(String programName, String methodName, Map<String, String> args, M3Parameter... fieldsToReturn) throws M3APIException {
        MvxSockJ mvxSockJ = createMvxSockJ(programName);
        setParameters(mvxSockJ, args);
        mvxSockJ.TRIM = false;
        mvxSockJ.mvxTrans("SetLstMaxRec 0000");
        ErrorLog log = createLog(programName, methodName, args);
        logRequest(log);
        try {
            int noOfErrors = mvxSockJ.mvxAccess(methodName);

            if (noOfErrors > 0) {
                String msg = mvxSockJ.mvxGetLastError();
                log.setErrorMessage(msg);
                logError(log);
                throw new M3APIException(msg);
            } else {
                if (fieldsToReturn == null || fieldsToReturn.length == 0) {
                    return null;
                } else {
                    List<Map<M3Parameter, String>> list = new ArrayList<Map<M3Parameter, String>>();
                    while(mvxSockJ.mvxMore()) {
                        Map<M3Parameter, String> fields = new HashMap<M3Parameter, String>(fieldsToReturn.length);
                        for (M3Parameter fieldName : fieldsToReturn) {
                            String fieldValue = mvxSockJ.mvxGetField(fieldName.getValue());
                            fields.put(fieldName, fieldValue);
                        }
                        list.add(fields);
                        mvxSockJ.mvxAccess(null);
                    }
                    return list;
                }
            }
        } catch (Exception e) {
        	LogTools.windowsEventLogError(programName);
        	LogTools.windowsEventLogError(e.getMessage());
            throw new M3APIException(e);
        } finally {
            mvxSockJ.mvxClose();
        }
    }

    private MvxSockJ createMvxSockJ(String programName) throws M3APIException {
        MvxSockJ mvxSockJ = new MvxSockJ(applicationProperty.getM3Server(),
                Integer.parseInt(applicationProperty.getM3Port()), "", 0, "");
        int noOfErrors = mvxSockJ.mvxInit("", applicationProperty.getM3Username(),
                applicationProperty.getM3Password(), programName);

        if (noOfErrors > 0) {
            try {
                mvxSockJ.mvxClose();
            } finally {
                throw new M3APIException(mvxSockJ.mvxGetLastError());
            }
        }

        try {
            mvxSockJ.setSoTimeout(0);
            mvxSockJ.mvxSetMaxWait(0);
        } catch (SocketException e) {
            throw new M3APIException(e);
        }
        return mvxSockJ;
    }

    /**
     * Common method to set the input parameters.
     *
     * @param mvxSockJ   the mvx sock j
     * @param parameters the parameters
     */
    private void setParameters(MvxSockJ mvxSockJ, Map<String, String> parameters) {
        Set<String> keys = parameters.keySet();
        for (Iterator<String> iterator = keys.iterator(); iterator.hasNext(); ) {
            String key = iterator.next();
            String value = parameters.get(key);
            mvxSockJ.mvxSetField(key, value);
        }
    }

    private ErrorLog createLog(String programName, String methodName, Map<String, String> args) {
        ErrorLog errorLog = new ErrorLog();
        errorLog.setWebServiceName(programName);
        errorLog.setColumnHeaders(appendAll(args.keySet()));
        errorLog.setColumnValues(appendAll(args.values()));
        errorLog.setFunctionName(methodName);
        errorLog.setDisplayProgram(false);
        errorLog.setNoOfTry(0);
        errorLog.setPriority(getPriority(programName));
        errorLog.setLoginUserID(applicationProperty.getM3Username());
        return errorLog;
    }

    private int getPriority(String programName) {
        return -1;
    }

    private String appendAll(Collection<String> values) {
        StringBuilder sb = new StringBuilder();
        for (String v : values) {
            sb.append(v).append(COMMA);
        }
        return sb.substring(0, sb.length() - COMMA.length());
    }

    private void logRequest(ErrorLog log) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("============");
        sb.append("\nServer: ").append(applicationProperty.getM3Server())
                .append("; port: ").append(applicationProperty.getM3Port())
                .append("\nM3 program: ").append(log.getWebServiceName())
                .append("\nM3 function: ").append(log.getFunctionName())
                .append("\nHeaders: ").append(log.getColumnHeaders())
                .append("\nValues: ").append(log.getColumnValues())
                .append("\n============");

        logger.info(sb.toString());
//        try {
//            m3UpdateDao.insertM3Request(log);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    private void logError(ErrorLog log) {
        logger.error(String.format("Request to M3 [%s].[%s] failed; message: %s",
                log.getWebServiceName(), log.getFunctionName(), log.getErrorMessage()));
//        try {
//            m3UpdateDao.insertErrorLogForM3WriteFailure(log);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
