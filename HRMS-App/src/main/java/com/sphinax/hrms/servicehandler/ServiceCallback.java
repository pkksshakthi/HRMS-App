package com.sphinax.hrms.servicehandler;

/**
 * Created by ganesaka on 12/24/2017.
 */

public interface ServiceCallback {

    /**It used give OPS service completed or failed **/

    void onSuccess(boolean flag);

    /** ReturnObject will send data recivied from serivce **/

    void onReturnObject(Object obj);

    /** ParseError appears while send any invalid data parsing to OPS **/

    void onParseError();

    /** It occurs while  Server not responding correctly **/

    void onNetworkError();

    /**It Internet connection not available **/

   // void onDataNotAvailable();

    /**It is error send by OPS service **/

    //void onError(String errorMessage);

    /** If any security problem rises in service call **/

    void unAuthorized();
}
